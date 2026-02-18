package br.com.streaming.application.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq; // Import necessário
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import br.com.streaming.application.ports.outbound.EventPublisherPort;
import br.com.streaming.application.ports.outbound.PaymentGatewayPort;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class RenewSubscriptionInteractorTest {

    @Mock
    private SubscriptionRepositoryPort repositoryPort;
    @Mock
    private PaymentGatewayPort paymentPort;
    @Mock
    private EventPublisherPort eventPort; // Nome deve bater com o campo no Interactor

    @InjectMocks
    private RenewSubscriptionInteractor interactor;

    @Test
    @DisplayName("Deve incrementar tentativas e manter ativa quando pagamento falhar e tentativas < 3")
    void shouldIncrementAttemptsAndKeepActiveWhenPaymentFails() {
        var id = UUID.randomUUID();
        var subscription = new Subscription(id, UUID.randomUUID(), Plan.PREMIUM, 
                            LocalDate.now(), LocalDate.now(), SubscriptionStatus.ACTIVE, 0);
        
        when(repositoryPort.findById(id)).thenReturn(Optional.of(subscription));
        when(paymentPort.charge(any(), any())).thenReturn(false);

        interactor.execute(id);

        assertEquals(1, subscription.getRenewalAttempts());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        verify(repositoryPort).save(subscription);
        verifyNoInteractions(eventPort);
    }

    @Test
    @DisplayName("Deve suspender assinatura e publicar evento quando atingir 3 falhas")
    void shouldSuspendAndPublishEventWhenMaxAttemptsReached() {
        var id = UUID.randomUUID();
        var subscription = new Subscription(id, UUID.randomUUID(), Plan.PREMIUM, 
                            LocalDate.now(), LocalDate.now(), SubscriptionStatus.ACTIVE, 2);
        
        when(repositoryPort.findById(id)).thenReturn(Optional.of(subscription));
        when(paymentPort.charge(any(), any())).thenReturn(false);

        interactor.execute(id);

        assertEquals(3, subscription.getRenewalAttempts());
        assertEquals(SubscriptionStatus.SUSPENDED, subscription.getStatus());
        verify(eventPort).publish(any(), eq(subscription)); 
    }
}