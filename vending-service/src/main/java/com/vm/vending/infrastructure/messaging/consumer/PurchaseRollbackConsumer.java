package com.vm.vending.infrastructure.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.common.domain.event.PurchaseRollbackEvent;
import com.vm.common.domain.model.Product;
import com.vm.vending.domain.model.VendingMachine;
import com.vm.vending.domain.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseRollbackConsumer {

    private final ObjectMapper objectMapper;
//    private final UserRepository userRepository;
    private final VendingMachineRepository vendingMachineRepository;

//    @RabbitListener(queues = "vending.event.purchase-rollback")
    public void consumePurchaseRollback(String payload) {
        try {
            PurchaseRollbackEvent event = objectMapper.readValue(payload, PurchaseRollbackEvent.class);

            log.info("Received rollback event: {}", event.getEventType());

            // TODO FIX
//            User user = userRepository.findById(event.getUserId())
//                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            VendingMachine machine = vendingMachineRepository.findById(event.getVendingMachineId())
                    .orElseThrow(() -> new IllegalArgumentException("Vending Machine not found"));

            Product product = machine.findProductById(event.getProductId());

//            user.increaseBalance(new Money(event.getPrice()));
            machine.increaseProductStock(product);

//            userRepository.save(user);
            vendingMachineRepository.save(machine);

            log.info("Rollback operation completed. Money refunded and stock restored: {}", product);

        } catch (Exception e) {
            log.error("Failed to process rollback event", e);
        }
    }
}
