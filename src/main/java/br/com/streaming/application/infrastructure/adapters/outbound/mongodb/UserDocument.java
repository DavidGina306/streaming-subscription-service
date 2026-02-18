package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class UserDocument {
    @Id
    private UUID id;
    private String name;
    private String email;
}