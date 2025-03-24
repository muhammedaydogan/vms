package com.vm.common.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseProductResponseDto {
    private String status; // örnek: "SUCCESS", "FAILED" // todo make enum
    private String message;
}