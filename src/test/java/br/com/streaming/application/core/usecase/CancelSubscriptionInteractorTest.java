package br.com.streaming.application.core.usecase;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.domain.exceptions.BusinessException;
import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelSubscriptionInteractorTest {

    @Mock
    private SubscriptionRepositoryPort repositoryPort;

    @InjectMocks
    private CancelSubscriptionInteractor interactor;

    @Test
    @DisplayName("Deve cancelar assinatura com sucesso mantendo a data de expiração original")
    void shouldCancelSubscriptionSuccessfully() {
        var id = UUID.randomUUID();
        var dataExpiracaoOriginal = LocalDate.now().plusDays(20);
        var subscription = new Subscription(
            id, 
            UUID.randomUUID(), 
            Plan.PREMIUM, 
            LocalDate.now(), 
            dataExpiracaoOriginal, 
            SubscriptionStatus.ACTIVE, 
            0
        );

        when(repositoryPort.findById(id)).thenReturn(Optional.of(subscription));

        interactor.execute(id);

        assertEquals(SubscriptionStatus.CANCELED, subscription.getStatus());
        assertEquals(dataExpiracaoOriginal, subscription.getExpirationDate(), "A data de expiração não deve mudar no cancelamento");
        verify(repositoryPort).save(subscription);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando tentar cancelar ID inexistente")
    void shouldThrowExceptionWhenSubscriptionNotFound() {
        var id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());
        var exception = assertThrows(BusinessException.class, () -> interactor.execute(id));
        assertTrue(exception.getMessage().contains("Subscription not found"));
    }
}