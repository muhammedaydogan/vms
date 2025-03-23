package com.vm.vending.api.controller;

import com.vm.common.application.command.PurchaseProductCommand;
import com.vm.vending.application.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Void> purchase(@RequestBody PurchaseProductCommand command) {
        purchaseService.handle(command);
        return ResponseEntity.ok().build();
    }
}
