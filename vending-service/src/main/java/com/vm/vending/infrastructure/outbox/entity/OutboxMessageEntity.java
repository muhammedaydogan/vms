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
@Builder
public class OutboxMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private UUID aggregateId;
    private String aggregateType;
    private String eventType;

    @Lob
    private String payload; // JSON


    // todo turn into enum
    private String status; // NEW, SENT, FAILED, DEAD

    private int retryCount;

    private LocalDateTime occurredAt;
    private LocalDateTime lastTriedAt;

    public void markAsSent() {
        this.status = "SENT";
    }

    public void markAsFailed() {
        this.status = "FAILED";
        this.retryCount += 1;
        this.lastTriedAt = LocalDateTime.now();
    }

    public void markAsDead() {
        this.status = "DEAD";
        this.lastTriedAt = LocalDateTime.now();
    }

    public boolean isRetryLimitExceeded(int maxRetry) {
        return this.retryCount >= maxRetry;
    }

    public boolean isPending() {
        return "NEW".equals(this.status) || "FAILED".equals(this.status);
    }
}
