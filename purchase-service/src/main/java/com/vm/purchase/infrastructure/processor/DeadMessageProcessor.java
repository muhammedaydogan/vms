package com.vm.purchase.infrastructure.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.common.domain.event.ProductPurchasedEvent;
import com.vm.common.domain.event.PurchaseRollbackEvent;
import com.vm.common.infrastructure.outbox.entity.OutboxMessageEntity;
import com.vm.purchase.infrastructure.outbox.repository.OutboxMessageRepository;
import com.vm.purchase.infrastructure.outbox.mapper.OutboxMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeadMessageProcessor {

    private final OutboxMessageRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 10_000) // her 10 saniyede bir tarar
    public void processDeadMessages() {
        List<OutboxMessageEntity> deadMessages = outboxRepository.findAll()
                .stream()
                .filter(msg -> "DEAD".equals(msg.getStatus()) && "ProductPurchasedEvent".equals(msg.getEventType()))
                .toList();

        for (OutboxMessageEntity deadMsg : deadMessages) {
            try {
                var event = objectMapper.readValue(deadMsg.getPayload(), ProductPurchasedEvent.class);

                PurchaseRollbackEvent rollback = PurchaseRollbackEvent.builder()
                        .userId(event.getUserId())
                        .vendingMachineId(event.getAggregateId())
                        .productId(event.getProductId())
                        .occurredAt(LocalDateTime.now())
                        .build();

                String rollbackJson = objectMapper.writeValueAsString(rollback);
                rabbitTemplate.convertAndSend("vending.events", "PurchaseRollbackEvent", rollbackJson);

                log.info("Sent PurchaseRollbackEvent for failed purchase id={}", event.getAggregateId());

            } catch (Exception e) {
                log.error("Failed to process dead message rollback", e);
            }
        }
    }
}
