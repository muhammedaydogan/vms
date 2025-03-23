package com.vm.user.domain.model;

import com.vm.common.domain.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private UUID id;
    private String username;
    private String passwordHash;
    private Money balance;

    public void increaseBalance(Money money) {
        this.balance = this.balance.add(money);
    }

    public void decreaseBalance(Money money) {
        this.balance = this.balance.subtract(money);
    }

    public boolean hasEnoughBalance(Money amount) {
        return this.balance.isGreaterThanOrEqual(amount);
    }
}
