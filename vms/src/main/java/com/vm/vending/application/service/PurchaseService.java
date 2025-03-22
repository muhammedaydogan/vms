package com.vm.vending.application.service;

import com.vm.vending.application.command.PurchaseProductCommand;
import com.vm.vending.domain.event.DomainEvent;
import com.vm.vending.domain.model.*;
import com.vm.vending.domain.repository.UserRepository;
import com.vm.vending.domain.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final VendingMachineRepository vendingMachineRepository;
    private final UserRepository userRepository;

    public List<DomainEvent> handle(PurchaseProductCommand command) {
        VendingMachine machine = vendingMachineRepository.findById(command.getVendingMachineId())
                .orElseThrow(() -> new IllegalArgumentException("Vending machine not found"));

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = findProductInMachine(machine, command.getProductId());

        machine.purchaseProduct(user, product);
        userRepository.save(user);
        vendingMachineRepository.save(machine);

        return machine.getDomainEvents(); // todo Outbox’a yazılacak
    }

    private Product findProductInMachine(VendingMachine machine, String productId) {
        return machine.getStock().keySet().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in machine"));
    }
}
