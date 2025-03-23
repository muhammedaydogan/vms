package com.vm.common.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginUserCommand {
    private UUID userId;
    private String passwordHash;
}