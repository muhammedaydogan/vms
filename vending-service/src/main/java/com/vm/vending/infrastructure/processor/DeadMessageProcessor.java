package com.vm.vending.infrastructure.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.common.domain.event.ProductPurchasedEvent;
import com.vm.common.domain.event.PurchaseRollbackEvent;
import com.vm.common.domain.model.Product;
import com.vm.vending.infrastructure.outbox.entity.OutboxMessageEntity;
import com.vm.vending.domain.model.VendingMachine;
import com.vm.vending.domain.repository.VendingMachineRepository;
import com.vm.vending.infrastructure.outbox.mapper.OutboxMapper;
import com.vm.vending.infrastructure.outbox.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeadMessageProcessor {

    private final ObjectMapper objectMapper;
    private final VendingMachineRepository vendingMachineRepository;
    private final OutboxMapper outboxMapper;
    private final OutboxMessageRepository outboxMessageRepository;

    public void process(String eventType, String payload) {
        try {
            if (eventType.equals("ProductPurchasedEvent")) {
                ProductPurchasedEvent failedEvent = objectMapper.readValue(payload, ProductPurchasedEvent.class);

                VendingMachine machine = vendingMachineRepository.findById(failedEvent.getAggregateId())
                        .orElseThrow(() -> new IllegalArgumentException("Vending Machine not found"));

//                Product product = machine.findProductById(failedEvent.getProductId());
//                int price = product.getPrice();
                int price = failedEvent.getPrice();

                PurchaseRollbackEvent rollbackEvent = PurchaseRollbackEvent.builder()
                        .userId(failedEvent.getUserId())
                        .productId(failedEvent.getProductId())
                        .vendingMachineId(failedEvent.getAggregateId())
                        .price(price)
                        .occurredAt(LocalDateTime.now())
                        .build();

                OutboxMessageEntity rollbackOutbox = outboxMapper.toOutboxMessage(rollbackEvent, "VendingMachine");
                outboxMessageRepository.save(rollbackOutbox);

                log.info("Rollback event written to Outbox: {}", rollbackEvent);
            }
        } catch (Exception e) {
            log.error("Failed to process DLQ message into rollback event", e);
        }
    }
}
