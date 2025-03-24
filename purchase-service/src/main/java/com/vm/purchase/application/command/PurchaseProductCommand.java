package com.vm.purchase.application.command;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class PurchaseProductCommand {
    UUID userId;
    UUID vendingMachineId;
    String productId;
}
