package br.com.streaming.application.infrastructure.adapters.inbound.rest.dto;


import br.com.streaming.application.core.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
@Schema(description = "Response object containing registered user details")
public class UserResponse {

    @Schema(description = "Unique identifier generated for the user", example = "550e8400-e29b-41d4-a716-446655440000")
    private final UUID id;

    @Schema(description = "Full name of the registered user", example = "David Oliveira")
    private final String name;

    @Schema(description = "Registered email address", example = "david.oliveira@email.com")
    private final String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}