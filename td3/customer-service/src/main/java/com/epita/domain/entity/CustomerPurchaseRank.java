package com.epita.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class CustomerPurchaseRank {
    private UUID user;
    private int rank;
    private int amount;

    public CustomerPurchaseRank(UUID user, int rank, int amount) {
        this.user = user;
        this.rank = rank;
        this.amount = amount;
    }
}
