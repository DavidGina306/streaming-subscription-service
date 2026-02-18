package br.com.streaming.application.infrastructure.adapters.outbound.kafka;

import br.com.streaming.application.ports.outbound.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, Object event) {
        kafkaTemplate.send(topic, event);
    }
}