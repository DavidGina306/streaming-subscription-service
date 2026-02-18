package br.com.streaming.application.infrastructure.adapters.inbound.rest.dto;

import br.com.streaming.application.core.enums.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(description = "Request object for creating a new subscription")
public class SubscriptionRequest {

    @NotNull(message = "userId is mandatory")
    @Schema(description = "Unique user ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @NotNull(message = "plan is mandatory")
    @Schema(description = "Plan chosen by the user", example = "PREMIUM")
    private Plan plan;

    @NotNull(message = "startDate is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Subscription start date", example = "2025-03-10")
    private LocalDate startDate;

    @NotNull(message = "expirationDate is mandatory")
    @Future(message = "expirationDate must be a future date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Subscription expiration date", example = "2025-04-10")
    private LocalDate expirationDate;
}