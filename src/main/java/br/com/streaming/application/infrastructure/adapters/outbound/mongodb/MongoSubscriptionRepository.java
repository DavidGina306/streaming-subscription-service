package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MongoSubscriptionRepository extends MongoRepository<SubscriptionDocument, UUID> {
    
    Optional<SubscriptionDocument> findByUserIdAndStatus(UUID userId, SubscriptionStatus status);

    List<SubscriptionDocument> findByExpirationDateAndStatusIn(LocalDate date, List<SubscriptionStatus> statuses);
}