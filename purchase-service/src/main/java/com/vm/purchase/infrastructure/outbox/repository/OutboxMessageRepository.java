package com.vm.purchase.infrastructure.outbox.repository;

import com.vm.common.infrastructure.outbox.entity.OutboxMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessageEntity, UUID> {
}
