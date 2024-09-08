package com.epita.domain.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;


@Setter
@Getter
@MongoEntity(collection="customers")
public class CustomerEntity {
    // Getters and Setters
    @BsonId
    private UUID id;
    private int purchases;

    // Constructors
    public CustomerEntity() {}

    public CustomerEntity(UUID id, int purchases) {
        this.id = id;
        this.purchases = purchases;
    }

}