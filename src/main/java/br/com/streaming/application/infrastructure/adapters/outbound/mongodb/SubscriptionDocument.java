package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subscriptions")
public class SubscriptionDocument {
    @Id
    private UUID id;
    private UUID userId;
    private Plan plan;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private SubscriptionStatus status;
    private int renewalAttempts;
}