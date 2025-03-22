package com.vm.vending.infrastructure.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class RabbitEventPublisher implements EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String eventType, String payload) {
        // topic = eventType -> exhange adi
        rabbitTemplate.convertAndSend(eventType, payload);
    }
}
