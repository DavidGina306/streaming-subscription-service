package br.com.streaming.application.infrastructure.adapters.outbound.payment;


import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.ports.outbound.PaymentGatewayPort;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.UUID;

@Component
public class PaymentGatewayAdapter implements PaymentGatewayPort {
    @Override
    public boolean charge(UUID userId, Plan plan) {
        return new Random().nextDouble() > 0.3; 
    }
}