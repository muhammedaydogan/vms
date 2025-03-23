package com.vm.vending.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "vending_machines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendingMachineEntity {

    @Id
    private UUID id;

    /**key is product id value is quantity*/
    @ElementCollection
    @CollectionTable(name = "vending_machine_stock", joinColumns = @JoinColumn(name = "machine_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<String, Integer> stock = new HashMap<>();
}
