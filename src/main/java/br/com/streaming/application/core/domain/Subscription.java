package br.com.streaming.application.core.domain;

import java.time.LocalDate;
import java.util.UUID;

import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.core.enums.SubscriptionStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Entidade representativa de uma assinatura")
public class Subscription {

    @Schema(description = "Identificador único da assinatura", example = "550e8400-e29b-41d4-a716-446655440000")
    private final UUID id;

    @Schema(description = "ID do usuário dono da assinatura")
    private final UUID userId;

    @Schema(description = "Plano contratado")
    private Plan plan;

    @Schema(description = "Data de início da assinatura")
    private LocalDate startDate;

    @Schema(description = "Data de expiração (próxima cobrança)")
    private LocalDate expirationDate;

    @Schema(description = "Status atual da assinatura")
    private SubscriptionStatus status;

    @Schema(description = "Contador de tentativas de renovação falhas", example = "0")
    private int renewalAttempts;

    public Subscription(UUID userId, Plan plan) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.plan = plan;
        this.startDate = LocalDate.now();
        this.expirationDate = LocalDate.now().plusMonths(1);
        this.status = SubscriptionStatus.ACTIVE;
        this.renewalAttempts = 0;
    }

    public Subscription(UUID id, UUID userId, Plan plan, LocalDate startDate,
            LocalDate expirationDate, SubscriptionStatus status, int renewalAttempts) {
        this.id = id;
        this.userId = userId;
        this.plan = plan;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.renewalAttempts = renewalAttempts;
    }

    /**
     * Lógica de Negócio: Registra falha e suspende se atingir 3 tentativas.
     */
    public void recordPaymentFailure() {
        this.renewalAttempts++;
        if (this.renewalAttempts >= 3) {
            this.status = SubscriptionStatus.SUSPENDED;
        }
    }

    /**
     * Lógica de Negócio: Renova por mais um mês e reseta tentativas.
     */
    public void renew() {
        this.startDate = LocalDate.now();
        this.expirationDate = LocalDate.now().plusMonths(1);
        this.status = SubscriptionStatus.ACTIVE;
        this.renewalAttempts = 0;
    }

    /**
     * Lógica de Negócio: Cancelamento solicitado pelo usuário.
     */
    public void cancel() {
        this.status = SubscriptionStatus.CANCELED;
    }
}