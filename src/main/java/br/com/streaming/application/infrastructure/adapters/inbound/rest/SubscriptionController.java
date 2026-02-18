package br.com.streaming.application.infrastructure.adapters.inbound.rest;


import br.com.streaming.application.ports.inbound.CreateSubscriptionUseCase;
import br.com.streaming.application.ports.inbound.CancelSubscriptionUseCase; // Novo Port
import br.com.streaming.application.infrastructure.adapters.inbound.rest.dto.SubscriptionRequest;
import br.com.streaming.application.infrastructure.adapters.inbound.rest.dto.SubscriptionResponse;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Endpoints para gestão de assinaturas de streaming")
public class SubscriptionController {

    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;

    @PostMapping
    @Operation(summary = "Cria uma nova assinatura")
    public ResponseEntity<SubscriptionResponse> create(@RequestBody @Valid SubscriptionRequest request) {
        var subscription = createSubscriptionUseCase.execute(request.getUserId(), request.getPlan());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SubscriptionResponse(subscription));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancela uma assinatura", description = "Muda o status para CANCELED, mas mantém acesso até o fim do período.")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        cancelSubscriptionUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}