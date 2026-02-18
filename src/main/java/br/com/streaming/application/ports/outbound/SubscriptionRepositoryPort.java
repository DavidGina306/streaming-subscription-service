package br.com.streaming.application.ports.outbound;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.streaming.application.core.domain.Subscription;

public interface SubscriptionRepositoryPort {
    Subscription save(Subscription subscription);
    Optional<Subscription> findById(UUID id);
    Optional<Subscription> findActiveByUserId(UUID userId);
    List<Subscription> findExpiringToday();
}