package com.vm.vending.api.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String passwordHash;
}
