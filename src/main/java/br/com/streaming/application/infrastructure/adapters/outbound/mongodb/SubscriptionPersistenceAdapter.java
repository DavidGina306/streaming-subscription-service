package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import br.com.streaming.application.infrastructure.adapters.mapper.SubscriptionMapper;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionPersistenceAdapter implements SubscriptionRepositoryPort {

    private final MongoSubscriptionRepository repository;

    @Override
    public Optional<Subscription> findActiveByUserId(UUID userId) {
        return repository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .map(SubscriptionMapper::toDomain);
    }

    @Override
    public List<Subscription> findExpiringToday() {
        LocalDate today = LocalDate.now();
        return repository.findByExpirationDateAndStatusIn(today, List.of(SubscriptionStatus.ACTIVE, SubscriptionStatus.CANCELED))
                .stream()
                .map(SubscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Subscription> findById(UUID id) {
        return repository.findById(id)
                .map(SubscriptionMapper::toDomain);
    }

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionDocument doc = SubscriptionMapper.toDocument(subscription);
        SubscriptionDocument saved = repository.save(doc);
        return SubscriptionMapper.toDomain(saved);
    }
}