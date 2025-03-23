package com.vm.vending.infrastructure.messaging.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void publish(String eventType, String payload) throws Exception {
        // topic = eventType
        kafkaTemplate.send(eventType, payload);
    }
}
