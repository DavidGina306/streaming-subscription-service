package br.com.streaming.application.core.usecase;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.ports.inbound.CancelSubscriptionUseCase;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelSubscriptionInteractor implements CancelSubscriptionUseCase {

    private final SubscriptionRepositoryPort repositoryPort;

    @Override
    public void execute(UUID subscriptionId) {
        // 1. Localiza a assinatura via Porta de Saída
        Subscription subscription = repositoryPort.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + subscriptionId));

        // 2. Executa a lógica de negócio definida no seu Modelo de Domínio
        // Lembra que no seu modelo o método cancel() apenas muda o status, 
        // mantendo a data de expiração intacta.
        subscription.cancel();

        // 3. Salva a alteração no MongoDB via Porta de Saída
        repositoryPort.save(subscription);
    }
}