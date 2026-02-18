package   br.com.streaming.application.ports.outbound;

import br.com.streaming.application.core.domain.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(java.util.UUID id);
}