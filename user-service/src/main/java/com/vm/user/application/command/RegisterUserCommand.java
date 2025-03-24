package com.vm.user.application.command;

import lombok.Data;

@Data
public class RegisterUserCommand {
    private String username;
    private String passwordHash;
}