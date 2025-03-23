package com.vm.vending.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginRequest {
    private UUID userId;
    private String passwordHash;
}