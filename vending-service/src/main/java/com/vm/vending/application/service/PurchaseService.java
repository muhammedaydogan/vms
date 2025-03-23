package com.vm.vending.application.service;

import com.vm.vending.application.command.PurchaseProductCommand;
import com.vm.vending.domain.event.DomainEvent;
import com.vm.vending.domain.model.Product;
import com.vm.vending.domain.model.User;
import com.vm.vending.domain.model.VendingMachine;
import com.vm.vending.domain.repository.UserRepository;
import com.vm.vending.domain.repository.VendingMachineRepository;
import com.vm.vending.infrastructure.outbox.mapper.OutboxMapper;
import com.vm.vending.infrastructure.outbox.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final VendingMachineRepository vendingMachineRepository;
    private final UserRepository userRepository;
    private final OutboxMapper outboxMapper;
    private final OutboxMessageRepository outboxMessageRepository;

    @Transactional
    public List<DomainEvent> handle(PurchaseProductCommand command) {
        VendingMachine machine = vendingMachineRepository.findById(command.getVendingMachineId())
                .orElseThrow(() -> new IllegalArgumentException("Vending machine not found"));

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = findProductInMachine(machine, command.getProductId());

        machine.purchaseProduct(user, product); // todo bu kisim ayri bir controller'a baglanip sepet mantigi yapilacak

        // save domain state
        userRepository.save(user);
        vendingMachineRepository.save(machine);

        // save domain events as outbox messages
        machine.getDomainEvents().forEach(domainEvent -> {
            var outboxMessageEntity = outboxMapper.toOutboxMessage(domainEvent, "VendingMachine");
            outboxMessageRepository.save(outboxMessageEntity);
        });

        // todo machine.clearDomainEvents();

        return machine.getDomainEvents();
    }

    private Product findProductInMachine(VendingMachine machine, String productId) {
        return machine.getStock().keySet().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in machine"));
    }
}
