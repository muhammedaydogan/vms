package com.vm.vending.infrastructure.persistence;

import com.vm.vending.infrastructure.persistence.entity.VendingMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendingMachineJpaRepository extends JpaRepository<VendingMachineEntity, UUID> {
}
