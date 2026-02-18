package br.com.streaming.application.core.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.ports.inbound.RenewSubscriptionUseCase;
import br.com.streaming.application.ports.outbound.PaymentGatewayPort;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import lombok.RequiredArgsConstructor;


import br.com.streaming.application.core.enums.SubscriptionStatus;
import br.com.streaming.application.ports.outbound.*;

@Service
@RequiredArgsConstructor
public class RenewSubscriptionInteractor implements RenewSubscriptionUseCase {

    private final SubscriptionRepositoryPort repositoryPort;
    private final PaymentGatewayPort paymentPort;
    private final EventPublisherPort publisherPort;

    @Override
    public void execute(UUID subscriptionId) {
        Subscription subscription = repositoryPort.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        boolean success = paymentPort.charge(subscription.getUserId(), subscription.getPlan());
        if (success) {
            subscription.renew();
            publisherPort.publish("subscription-renewed", subscription);
        } else {
            subscription.recordPaymentFailure();
            if (subscription.getStatus() == SubscriptionStatus.SUSPENDED) {
                publisherPort.publish("subscription-suspended", subscription);
            }
        }
        repositoryPort.save(subscription);
    }
}