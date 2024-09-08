package com.epita.domain.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;


@Setter
@Getter
public class CustomerEntity {
    // Getters and Setters
    private UUID id;
    private int purchases;

    // Constructors
    public CustomerEntity() {}

    public CustomerEntity(UUID id, int purchases) {
        this.id = id;
        this.purchases = purchases;
    }

}