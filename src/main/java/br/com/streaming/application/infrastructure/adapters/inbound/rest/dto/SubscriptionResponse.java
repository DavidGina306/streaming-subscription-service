package br.com.streaming.application.infrastructure.adapters.inbound.rest.dto;

import br.com.streaming.application.core.domain.Subscription;
import lombok.Getter;
import java.util.UUID;

@Getter 
public class SubscriptionResponse {
    private final UUID usuarioId;
    private final String plano;
    private final String status;

    public SubscriptionResponse(Subscription s) {
        this.usuarioId = s.getUserId();
        this.plano = s.getPlan().name();
        this.status = s.getStatus().name().equals("ACTIVE") ? "ATIVA" : s.getStatus().name();
    }
}