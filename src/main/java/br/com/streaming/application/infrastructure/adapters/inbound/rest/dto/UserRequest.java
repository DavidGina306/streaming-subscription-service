package br.com.streaming.application.infrastructure.adapters.inbound.rest.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "User registration request object")
public class UserRequest {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Full name of the user", example = "David Oliveira")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", example = "david.oliveira@email.com")
    private String email;
}