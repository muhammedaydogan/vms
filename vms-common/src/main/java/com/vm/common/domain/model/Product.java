package com.vm.common.domain.model;

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
    /**
     * represents <strong>turkish lira</strong>
     */
    private int price;
}
