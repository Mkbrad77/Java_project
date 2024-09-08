package com.epita.domain.entity;

import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReceipt {
    private UUID purchaseId;
    private UUID customerId;
    private List<ProductEntity> products;

}
