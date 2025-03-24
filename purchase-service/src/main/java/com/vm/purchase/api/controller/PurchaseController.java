package com.vm.purchase.api.controller;

import com.vm.common.api.dto.request.PurchaseProductRequestDto;
import com.vm.common.api.dto.response.PurchaseProductResponseDto;
import com.vm.common.domain.event.DomainEvent;
import com.vm.purchase.application.command.PurchaseProductCommand;
import com.vm.purchase.application.mapper.DtoToCommandMapper;
import com.vm.purchase.application.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final DtoToCommandMapper dtoToCommandMapper;

    @PostMapping
    public ResponseEntity<PurchaseProductResponseDto> purchase(@RequestBody PurchaseProductRequestDto requestDto) {
        try {
            PurchaseProductCommand command = dtoToCommandMapper.toCommand(requestDto);
            List<DomainEvent> events = purchaseService.handle(command);
            return ResponseEntity.ok(dtoToCommandMapper.toResponse("SUCCESS", "Purchase completed"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(dtoToCommandMapper.toResponse("ERROR", e.getMessage()));
        }
    }
}
