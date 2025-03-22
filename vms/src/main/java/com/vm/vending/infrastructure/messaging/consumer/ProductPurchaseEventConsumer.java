package com.vm.vending.infrastructure.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.vending.domain.event.ProductPurchasedEvent;
import com.vm.vending.domain.event.PurchaseConfirmedEvent;
import com.vm.vending.infrastructure.outbox.entity.OutboxMessageEntity;
import com.vm.vending.infrastructure.outbox.mapper.OutboxMapper;
import com.vm.vending.infrastructure.outbox.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductPurchaseEventConsumer {
    private final ObjectMapper objectMapper;
    private final OutboxMapper outboxMapper;
    private final OutboxMessageRepository outboxMessageRepository;

    @RabbitListener(queues = "ProductPurchasedEvent")
    public void handleMessage(String messageJson) {
        try {
            ProductPurchasedEvent event = objectMapper.readValue(messageJson, ProductPurchasedEvent.class);
            log.info("Received ProductPurchasedEvent: {}", event);

            // Assume operation successful
            PurchaseConfirmedEvent ackEvent = new PurchaseConfirmedEvent(
                    event.getAggregateId(),
                    event.getUserId(),
                    event.getProductId(),
                    LocalDateTime.now()
            );

            // ACK event Outbox'a gider.
            OutboxMessageEntity outboxAck = outboxMapper.toOutboxMessage(ackEvent, "VendingMachine");
            outboxMessageRepository.save(outboxAck);

        } catch (Exception e) {
            log.error("Error processing ProductPurchasedEvent", e);
        }
    }

    // Test DLQ
//    @RabbitListener(queues = "ProductPurchasedEvent")
//    public void handle(String payload) {
//        throw new RuntimeException("DLQ testi için bilerek hata");
//    }
}
