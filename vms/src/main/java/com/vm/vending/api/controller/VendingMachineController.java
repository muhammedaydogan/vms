package com.vm.vending.api.controller;

import com.vm.vending.api.dto.ProductStockDto;
import com.vm.vending.application.service.VendingMachineService;
import com.vm.vending.domain.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/machines")
@RequiredArgsConstructor
public class VendingMachineController {
    private final VendingMachineService vendingMachineService;

    @GetMapping("")
    public List<ProductStockDto> getAvailableProducts(@PathVariable UUID machineId) {
        return vendingMachineService.getAllAvailableProductsByMachine(machineId).stream()
                .map(entry -> new ProductStockDto(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getKey().getPrice(),
                        entry.getValue()
                ))
                .toList();
    }
}
