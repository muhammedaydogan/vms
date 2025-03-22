package com.vm.vending.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    private String id;
    private String name;
    private int price; // birim cinsinden (örnek: kuruş)
}
