package com.vm.vending.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class AddBalanceCommand {
    UUID userId;
    Integer balance;
}
