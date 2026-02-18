package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserMongoRepository extends MongoRepository<UserDocument, UUID> {
    Optional<UserDocument> findByEmail(String email);
}