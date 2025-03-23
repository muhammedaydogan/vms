package com.vm.vending.infrastructure.config;

import com.vm.common.domain.model.Product;
import com.vm.common.domain.model.VendingMachine;
import com.vm.vending.domain.repository.VendingMachineRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class VendingMachineInitializer {

    private final VendingMachineRepository vendingMachineRepository;

    @PostConstruct
    public void initializeStock() {
        VendingMachine machine = new VendingMachine(UUID.randomUUID(), new HashMap<>());
        getProductsFromSensors().forEach(p -> machine.getStock().put(p, 10));
        vendingMachineRepository.save(machine);
    }

    /**
     * Simulates <strong>incoming product info</strong> from some sensors
     */
    public List<Product> getProductsFromSensors() {
        return List.of(
                new Product("1", "Su", 25),
                new Product("2", "Kola", 35),
                new Product("3", "Soda", 45),
                new Product("4", "Snickers", 50),
                new Product("5", "Cips", 40),
                new Product("6", "Çikolata", 30),
                new Product("7", "Enerji İçeceği", 60),
                new Product("8", "Meyve Suyu", 55),
                new Product("9", "Protein Bar", 45),
                new Product("10", "Sakız", 20)
        );
    }
}
