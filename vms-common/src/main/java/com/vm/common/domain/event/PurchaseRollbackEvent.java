package com.vm.common.domain.event;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRollbackEvent implements DomainEvent {

    private UUID userId;
    private UUID vendingMachineId;
    private String productId;
    private int price;
    private LocalDateTime occurredAt;

    @Override
    public UUID getAggregateId() {
        return vendingMachineId;
    }

    @Override
    public String getEventType() {
        return "PurchaseRollbackEvent";
    }
}
