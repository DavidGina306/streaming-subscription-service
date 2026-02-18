package br.com.streaming.application.core.usecase;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.domain.exceptions.BusinessException;
import br.com.streaming.application.ports.inbound.CancelSubscriptionUseCase;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelSubscriptionInteractor implements CancelSubscriptionUseCase {

    private final SubscriptionRepositoryPort repositoryPort;

    @Override
    public void execute(UUID subscriptionId) {
        Subscription subscription = repositoryPort.findById(subscriptionId)
                .orElseThrow(() -> new BusinessException("Subscription not found with ID: " + subscriptionId));
        subscription.cancel();
        repositoryPort.save(subscription);
    }
}