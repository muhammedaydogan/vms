package com.vm.vending.domain.repository;

import com.vm.vending.domain.model.VendingMachine;
import java.util.Optional;
import java.util.UUID;

public interface VendingMachineRepository {
    void save(VendingMachine machine);
    Optional<VendingMachine> findById(UUID id);
}
