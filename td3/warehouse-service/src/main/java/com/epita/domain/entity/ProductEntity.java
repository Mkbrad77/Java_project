package com.epita.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductEntity { //Product
    private String name;
    private int quantity;

    public ProductEntity() {}

    public ProductEntity(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

}
