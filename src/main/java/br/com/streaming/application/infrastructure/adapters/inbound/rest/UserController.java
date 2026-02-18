package br.com.streaming.application.infrastructure.adapters.inbound.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.streaming.application.infrastructure.adapters.inbound.rest.dto.UserRequest;
import br.com.streaming.application.infrastructure.adapters.inbound.rest.dto.UserResponse;
import br.com.streaming.application.ports.inbound.CreateUserUseCase;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for user management") 
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    @Operation(summary = "Register a new user", description = "Creates a user in the database and returns the generated UUID")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        var user = createUserUseCase.execute(request.getName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
    }
}