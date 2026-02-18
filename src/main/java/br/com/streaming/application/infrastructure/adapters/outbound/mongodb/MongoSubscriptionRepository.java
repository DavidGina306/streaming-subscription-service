package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MongoSubscriptionRepository extends MongoRepository<SubscriptionDocument, UUID> {
    
    // Busca assinatura ativa por usuário
    Optional<SubscriptionDocument> findByUserIdAndStatus(UUID userId, SubscriptionStatus status);

    // Busca assinaturas que vencem hoje e estão ativas ou canceladas (ainda no prazo)
    List<SubscriptionDocument> findByExpirationDateAndStatusIn(LocalDate date, List<SubscriptionStatus> statuses);
}