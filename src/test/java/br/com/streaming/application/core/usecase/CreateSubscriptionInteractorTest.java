package br.com.streaming.application.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.domain.exceptions.BusinessException;
import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateSubscriptionInteractorTest {

    @Mock
    private SubscriptionRepositoryPort repositoryPort;

    @InjectMocks
    private CreateSubscriptionInteractor interactor;

    @Test
    @DisplayName("Deve lançar exceção quando o usuário já possui uma assinatura ativa")
    void shouldThrowExceptionWhenUserAlreadyHasActiveSubscription() {
        // GIVEN
        var userId = UUID.randomUUID();
        var existingSubscription = new Subscription(userId, Plan.BASICO);
        
        // Usando o seu método findActiveByUserId
        when(repositoryPort.findActiveByUserId(userId))
            .thenReturn(Optional.of(existingSubscription));

        // WHEN & THEN
        var exception = assertThrows(BusinessException.class, () -> {
            interactor.execute(userId, Plan.PREMIUM);
        });

        assertEquals("The user already has an active subscription.", exception.getMessage());
        verify(repositoryPort, never()).save(any());
    }
}