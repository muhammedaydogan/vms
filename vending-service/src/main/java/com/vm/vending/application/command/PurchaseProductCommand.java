package com.vm.vending.application.command;

import lombok.Value;

import java.util.UUID;

@Value
public class PurchaseProductCommand {
    UUID userId;
    UUID vendingMachineId;
    String productId;
}
