package br.com.streaming.application.ports.inbound;

import java.util.UUID;

public interface CancelSubscriptionUseCase {
    void execute(UUID subscriptionId);
}