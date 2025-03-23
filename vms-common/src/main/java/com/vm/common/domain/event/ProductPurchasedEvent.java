package com.vm.common.domain.event;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@AllArgsConstructor
public class ProductPurchasedEvent implements DomainEvent {
    UUID aggregateId; // vending machine id
    UUID userId;
    String productId;
    LocalDateTime occurredAt;

    @Override
    public String getEventType() {
        return "ProductPurchasedEvent";
    }
}
