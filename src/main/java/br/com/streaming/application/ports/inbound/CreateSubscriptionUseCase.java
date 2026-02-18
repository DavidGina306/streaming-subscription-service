package br.com.streaming.application.ports.inbound;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.enums.Plan;
import java.util.UUID;

public interface CreateSubscriptionUseCase {
    Subscription execute(UUID userId, Plan plan);
}