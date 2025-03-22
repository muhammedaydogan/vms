package com.vm.vending.application.command;

import lombok.Value;

import java.util.UUID;

@Value
public class PurchageProductCommand {
    UUID userId;
    UUID vendingMachineId;
    String productId;
}
