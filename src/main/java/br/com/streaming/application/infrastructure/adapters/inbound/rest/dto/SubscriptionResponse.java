package br.com.streaming.application.infrastructure.adapters.inbound.rest.dto;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.core.enums.SubscriptionStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter 
public class SubscriptionResponse {
  private UUID id;
    private UUID usuarioId;
    private Plan plano;
    private LocalDate dataInicio;
    private LocalDate dataExpiracao;
    private SubscriptionStatus status;

    public SubscriptionResponse(Subscription s) {
        this.id = s.getId();
        this.usuarioId = s.getUserId();
        this.plano = s.getPlan();
        this.dataInicio = s.getStartDate();
        this.dataExpiracao = s.getExpirationDate();
        this.status = s.getStatus();
    }
}