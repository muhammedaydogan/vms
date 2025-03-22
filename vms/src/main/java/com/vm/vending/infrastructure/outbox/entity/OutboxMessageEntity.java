package com.vm.vending.infrastructure.outbox.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutboxMessageEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID aggregateId;

    private String aggregateType;

    private String eventType;

    @Lob
    private String payload; // JSON

    private LocalDateTime occurredAt;

    private String status; // NEW, SENT, FAILED

    private LocalDateTime createdAt;
}
