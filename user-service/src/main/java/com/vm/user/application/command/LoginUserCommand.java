package com.vm.user.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginUserCommand {
    private UUID userId;
    private String passwordHash;
}