package com.vm.vending.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddBalanceRequest {
    UUID userId;
    Integer balance;
}
