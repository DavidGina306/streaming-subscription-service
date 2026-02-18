package br.com.streaming.application.infrastructure.adapters.mapper;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.infrastructure.adapters.outbound.mongodb.SubscriptionDocument;

public class SubscriptionMapper {
    public static SubscriptionDocument toDocument(Subscription domain) {
        if (domain == null) return null;
        return SubscriptionDocument.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .plan(domain.getPlan())
                .startDate(domain.getStartDate())
                .expirationDate(domain.getExpirationDate())
                .status(domain.getStatus())
                .renewalAttempts(domain.getRenewalAttempts())
                .build();
    }

    public static Subscription toDomain(SubscriptionDocument doc) {
        if (doc == null) return null;
        return new Subscription(
                doc.getId(), doc.getUserId(), doc.getPlan(),
                doc.getStartDate(), doc.getExpirationDate(),
                doc.getStatus(), doc.getRenewalAttempts()
        );
    }
}