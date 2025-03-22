package com.vm.vending.infrastructure.outbox.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.vending.domain.event.DomainEvent;
import com.vm.vending.infrastructure.outbox.entity.OutboxMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OutboxMapper {

    private final ObjectMapper objectMapper;

    public OutboxMessageEntity toOutboxMessage(DomainEvent event, String aggregateType) {
        try {
            return new OutboxMessageEntity(
                    null,
                    event.getAggregateId(),
                    aggregateType,
                    event.getEventType(),
                    objectMapper.writeValueAsString(event),
                    event.getOccurredAt(),
                    "NEW",
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to map event to Outbox", e);
        }
    }
}
