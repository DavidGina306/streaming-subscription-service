package   br.com.streaming.application.ports.outbound;
import java.util.UUID;

import br.com.streaming.application.core.enums.Plan;

public interface PaymentGatewayPort {
    boolean charge(UUID userId, Plan plan);
}