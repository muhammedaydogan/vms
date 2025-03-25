package com.vm.user.infrastructure.outbox.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.common.domain.event.DomainEvent;
import com.vm.user.infrastructure.outbox.entity.OutboxMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OutboxMapper {

    private final ObjectMapper objectMapper;

    public OutboxMessageEntity toOutboxMessage(DomainEvent event, String aggregateType) {
        try {
            return new OutboxMessageEntity().builder()
                    .aggregateId(event.getAggregateId())
                    .aggregateType(aggregateType)
                    .eventType(event.getEventType())
                    .payload(objectMapper.writeValueAsString(event))
                    .status("NEW")
                    .retryCount(0)
                    .occurredAt(event.getOccurredAt())
                    .lastTriedAt(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to map event to Outbox", e);
        }
    }
}
