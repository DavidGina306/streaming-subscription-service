package br.com.streaming.application.infrastructure.adapters.inbound.rest;


import br.com.streaming.application.core.enums.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Objeto de requisição para criar uma nova assinatura")
public class SubscriptionRequest {

    @Schema(description = "ID único do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @Schema(description = "Plano escolhido pelo usuário", example = "PREMIUM")
    private Plan plan;
}