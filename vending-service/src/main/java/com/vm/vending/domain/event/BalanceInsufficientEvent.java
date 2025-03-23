package com.vm.vending.domain.event;

import lombok.Value;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class BalanceInsufficientEvent implements DomainEvent {
    UUID aggregateId;
    UUID userId;
    int requiredAmount;
    LocalDateTime occurredAt;

    @Override
    public String getEventType() {
        return "BalanceInsufficientEvent";
    }
}