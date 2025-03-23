package com.vm.vending.infrastructure.outbox.repository;

import com.vm.vending.infrastructure.outbox.entity.OutboxMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessageEntity, UUID> {
}
