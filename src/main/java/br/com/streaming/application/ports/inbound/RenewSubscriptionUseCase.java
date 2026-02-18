package br.com.streaming.application.ports.inbound;

import java.util.UUID;

public interface RenewSubscriptionUseCase {
    void execute(UUID subscriptionId);
}