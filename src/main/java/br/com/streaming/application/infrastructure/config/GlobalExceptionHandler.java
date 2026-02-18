package br.com.streaming.application.infrastructure.config;

import br.com.streaming.application.core.domain.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de Regra de Negócio (ex: Assinatura duplicada)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "Regra de Negócio");
    }

    // Captura erros de "Não Encontrado" (404)
    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<Object> handleNotFound(org.springframework.web.server.ResponseStatusException ex) {
        return buildResponse("O recurso solicitado não foi encontrado.", HttpStatus.NOT_FOUND, "Não Encontrado");
    }

    // Captura qualquer outro erro inesperado (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildResponse("Ocorreu um erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR, "Erro Interno");
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status, String errorType) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", errorType);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}