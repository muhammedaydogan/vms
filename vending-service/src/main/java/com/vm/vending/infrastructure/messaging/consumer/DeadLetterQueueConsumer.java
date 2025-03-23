package com.vm.vending.infrastructure.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.vending.infrastructure.messaging.processor.DeadMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    private final DeadMessageProcessor deadMessageProcessor;

    public DeadLetterQueueConsumer(DeadMessageProcessor deadMessageProcessor) {
        this.deadMessageProcessor = deadMessageProcessor;
    }

    @RabbitListener(queues = "event.dlq")
    public void handleDeadLetter(String payload) {
        log.warn("DLQ message received: {}", payload);

        try {
            String eventType = extractEventTypeFromJson(payload);
            deadMessageProcessor.process(eventType, payload);
        } catch (Exception e) {
            log.error("DLQ message processing failed", e);
        }
    }

    private String extractEventTypeFromJson(String payload) {
        try {
            return new ObjectMapper().readTree(payload).get("eventType").asText();
        } catch (Exception e) {
            throw new RuntimeException("Could not extract eventType from DLQ message", e);
        }
    }
}
