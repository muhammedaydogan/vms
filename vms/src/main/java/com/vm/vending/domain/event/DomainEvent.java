package com.vm.vending.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();
    LocalDateTime getOccurredAt();
    String getEventType();
}