package br.com.streaming.application.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.streaming.application.ports.inbound.RenewSubscriptionUseCase;
import br.com.streaming.application.ports.outbound.SubscriptionRepositoryPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionRenewalScheduler {
    private final SubscriptionRepositoryPort repositoryPort;
    private final RenewSubscriptionUseCase renewUseCase;

    @Scheduled(cron = "0 0 0 * * *")
    public void run() {
        repositoryPort.findExpiringToday().forEach(sub -> 
            renewUseCase.execute(sub.getId())
        );
    }
}