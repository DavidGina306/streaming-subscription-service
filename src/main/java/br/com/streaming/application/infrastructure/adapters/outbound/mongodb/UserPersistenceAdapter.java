package br.com.streaming.application.infrastructure.adapters.outbound.mongodb;

import br.com.streaming.application.core.domain.User;
import br.com.streaming.application.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserMongoRepository repository;

    @Override
    public User save(User user) {
        // Converte Domínio -> Documento (Entrada)
        UserDocument document = new UserDocument(
            user.getId(),
            user.getName(),
            user.getEmail()
        );

        UserDocument saved = repository.save(document);

        // Converte Documento -> Domínio (Saída)
        return new User(saved.getId(), saved.getName(), saved.getEmail());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(doc -> new User(doc.getId(), doc.getName(), doc.getEmail()));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id)
                .map(doc -> new User(doc.getId(), doc.getName(), doc.getEmail()));
    }
}