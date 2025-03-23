package com.vm.vending.application.service;

import com.vm.vending.domain.model.Product;
import com.vm.vending.domain.model.VendingMachine;
import com.vm.vending.domain.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendingMachineService {

    private final VendingMachineRepository vendingMachineRepository;

    public List<Map.Entry<Product, Integer>> getAllAvailableProductsByMachine(UUID machineId) {
        VendingMachine machine = vendingMachineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Vending machine not found"));
        return machine.getStock().entrySet().stream()
                .filter(entry -> machine.isProductAvailable(entry.getKey()))
                .toList();
    }
}
