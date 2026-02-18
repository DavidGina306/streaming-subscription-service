package br.com.streaming.application.infrastructure.adapters.inbound.rest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.streaming.application.core.domain.Subscription;
import br.com.streaming.application.core.enums.Plan;
import br.com.streaming.application.ports.inbound.CreateSubscriptionUseCase;
import br.com.streaming.application.ports.inbound.CancelSubscriptionUseCase; // Importante para o MockBean

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateSubscriptionUseCase createSubscriptionUseCase;

    @MockBean
    private CancelSubscriptionUseCase cancelSubscriptionUseCase; // Resolve o erro de dependência insatisfeita

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve receber payload JSON e criar assinatura com sucesso")
    void shouldCreateSubscriptionFromPayload() throws Exception {
        // GIVEN
        var userId = UUID.randomUUID();
        var payload = """
                {
                    "userId": "%s",
                    "plan": "PREMIUM"
                }
                """.formatted(userId);

        var expectedSubscription = new Subscription(userId, Plan.PREMIUM);
        
        when(createSubscriptionUseCase.execute(eq(userId), eq(Plan.PREMIUM)))
            .thenReturn(expectedSubscription);

        // WHEN & THEN
        mockMvc.perform(post("/v1/subscriptions") // Certifique-se que o caminho inclui o /api se configurado no properties
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuarioId").value(userId.toString()))
                .andExpect(jsonPath("$.plano").value("PREMIUM"))
                .andExpect(jsonPath("$.status").value("ATIVA"));
    }
}