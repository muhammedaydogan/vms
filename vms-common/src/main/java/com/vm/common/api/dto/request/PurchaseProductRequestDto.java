package com.vm.common.api.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class PurchaseProductRequestDto {
    private UUID userId;
    private UUID vendingMachineId;
    private String productId;
}
