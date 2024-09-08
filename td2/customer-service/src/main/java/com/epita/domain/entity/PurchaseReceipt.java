package com.epita.domain.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@MongoEntity(collection="purchases")
public class PurchaseReceipt {
    @BsonId
    public UUID id;
    public UUID customerId;
    private List<ProductEntity> products;
    public boolean succeed;
    public String message;
    public PurchaseReceipt() {
    }
    public PurchaseReceipt(UUID customerId, List<ProductEntity> products) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.products = products;
    }
}
