package br.com.streaming.application.core.usecase;

import br.com.streaming.application.core.domain.User;
import br.com.streaming.application.core.domain.exceptions.BusinessException;
import br.com.streaming.application.ports.inbound.CreateUserUseCase;
import br.com.streaming.application.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateUserInteractor implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User execute(String name, String email) {
        if (userRepositoryPort.findByEmail(email).isPresent()) {
            throw new BusinessException("User with this email already exists");
        }

        User newUser = new User(UUID.randomUUID(), name, email);
        return userRepositoryPort.save(newUser);
    }
}