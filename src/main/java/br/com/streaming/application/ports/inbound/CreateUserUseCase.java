package br.com.streaming.application.ports.inbound;

import br.com.streaming.application.core.domain.User;

public interface CreateUserUseCase {
    User execute(String name, String email);
}