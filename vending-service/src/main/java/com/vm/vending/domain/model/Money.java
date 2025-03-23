package com.vm.vending.domain.model;

import lombok.Value;

@Value
public class Money {
    int amount;

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other) {
        return new Money(this.amount - other.amount);
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount >= other.amount;
    }

    public boolean isNegative() {
        return this.amount < 0;
    }
}
