package com.vm.vending.infrastructure.persistence;

import com.vm.common.domain.model.Product;
import com.vm.common.domain.model.VendingMachine;
import com.vm.vending.domain.repository.VendingMachineRepository;
import com.vm.vending.infrastructure.persistence.entity.VendingMachineEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VendingMachineRepositoryImpl implements VendingMachineRepository {

    private final VendingMachineJpaRepository jpaRepository;

    @Override
    public void save(VendingMachine machine) {
        VendingMachineEntity entity = mapToEntity(machine);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<VendingMachine> findById(UUID id) {
        return jpaRepository.findById(id).map(this::mapToDomain);
    }

    private VendingMachineEntity mapToEntity(VendingMachine domain) {
        VendingMachineEntity entity = new VendingMachineEntity();
        entity.setId(domain.getId());

        // Product ID -> quantity
        Map<String, Integer> stock = new HashMap<>();
        domain.getStock().forEach((product, quantity) ->
                stock.put(product.getId(), quantity)
        );

        entity.setStock(stock);
        return entity;
    }

    private VendingMachine mapToDomain(VendingMachineEntity entity) {
        // TODO: Product detayları veritabanından ayrı alınmalı
        // Simdilik Basit tutuyoruz
        Map<Product, Integer> stock = new HashMap<>();
        entity.getStock().forEach((productId, quantity) -> {
            Product product = new Product(productId, "Unknown", 0); // geçici
            stock.put(product, quantity);
        });

        return new VendingMachine(entity.getId(), stock);
    }
}
