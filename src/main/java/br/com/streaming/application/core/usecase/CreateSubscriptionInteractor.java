package br.com.streaming.application.core.usecase;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.domain.exceptions.BusinessException;
import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.ports.inbound.CreateSubscriptionUseCase;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateSubscriptionInteractor implements CreateSubscriptionUseCase {

    private final SubscriptionRepositoryPort repositoryPort;

    @Override
    public Subscription execute(UUID userId, Plan plan) {
        repositoryPort.findActiveByUserId(userId).ifPresent(s -> {
            throw new BusinessException("The user already has an active subscription.");
        });
        Subscription newSubscription = new Subscription(userId, plan);
        return repositoryPort.save(newSubscription);
    }
}