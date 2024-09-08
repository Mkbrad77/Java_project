package com.epita.domain.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

@Setter
@Getter
@MongoEntity(collection="products")
public class ProductEntity { //Product
    @BsonId
    private String name;
    private int quantity;

    public ProductEntity() {}

    public ProductEntity(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

}
