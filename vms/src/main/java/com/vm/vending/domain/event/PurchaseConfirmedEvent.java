package com.vm.vending.domain.event;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@AllArgsConstructor
public class PurchaseConfirmedEvent implements DomainEvent {
    UUID aggregateId; // aggrete'imiz vending machine
    UUID userId;
    String productId;
    LocalDateTime occurredAt;

    @Override
    public String getEventType() {
        return "PurchaseConfirmedEvent";
    }
}
