package br.com.streaming.application.ports.outbound;

public interface EventPublisherPort {
    void publish(String topic, Object event);
}