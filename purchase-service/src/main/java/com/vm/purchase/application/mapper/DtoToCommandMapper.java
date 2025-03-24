package com.vm.purchase.application.mapper;

import com.vm.common.api.dto.request.PurchaseProductRequestDto;
import com.vm.common.api.dto.response.PurchaseProductResponseDto;
import com.vm.purchase.application.command.PurchaseProductCommand;
import org.springframework.stereotype.Component;

@Component
public class DtoToCommandMapper {
    public PurchaseProductCommand toCommand(PurchaseProductRequestDto dto) {
        return PurchaseProductCommand.builder()
                .userId(dto.getUserId())
                .vendingMachineId(dto.getVendingMachineId())
                .productId(dto.getProductId())
                .build();
    }

    public PurchaseProductResponseDto toResponse(String status, String message) {
        return new PurchaseProductResponseDto(status, message);
    }

}
