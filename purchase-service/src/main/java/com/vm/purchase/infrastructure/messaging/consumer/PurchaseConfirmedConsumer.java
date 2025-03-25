package com.vm.purchase.infrastructure.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.common.domain.event.PurchaseConfirmedEvent;
import com.vm.purchase.infrastructure.outbox.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseConfirmedConsumer {
    private final ObjectMapper objectMapper;
    private final OutboxMessageRepository outboxMessageRepository;

    @RabbitListener(queues = "PurchaseConfirmedEvent")
    public void consume(String payload) {
        try {
            PurchaseConfirmedEvent event = objectMapper.readValue(payload, PurchaseConfirmedEvent.class);

            outboxMessageRepository.findByAggregateIdAndEventType(event.getAggregateId(), "ProductPurchasedEvent")
                    .ifPresentOrElse(outboxMessageEntity -> {
                        outboxMessageEntity.markAsConfirmed();
                        outboxMessageRepository.save(outboxMessageEntity);
                        log.info("Outbox message CONFIRMED for id {}", outboxMessageEntity.getId());
                    }, () -> log.warn("Outbox message not found for aggregateId {}", event.getAggregateId()));
        } catch (JsonProcessingException e) {
            log.error("Failed to process PurchaseConfirmedEvent", e);
        }
    }
}
