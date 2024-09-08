package com.epita.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPopularity {
    private String name;
    private int rank;
    private int customers;

    public ProductPopularity(String name, int rank, int customers) {
        this.name = name;
        this.rank = rank;
        this.customers = customers;
    }
}
