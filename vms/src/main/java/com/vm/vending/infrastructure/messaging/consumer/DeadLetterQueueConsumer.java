package com.vm.vending.infrastructure.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "event.dlq")
    public void handleDeadLetter(String payload) {
        log.warn("DLQ message received: {}", payload);
        // todo add retry mechanism
    }
}
