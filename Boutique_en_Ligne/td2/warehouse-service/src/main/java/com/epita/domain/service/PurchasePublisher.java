package com.epita.domain.service;

import com.epita.domain.entity.PurchaseReceipt;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PurchasePublisher {
    private final PubSubCommands<PurchaseReceipt> publisher;

    @Inject
    public PurchasePublisher(RedisDataSource ds) {
        publisher = ds.pubsub(PurchaseReceipt.class);
    }

    public void publish(PurchaseReceipt command) {
        publisher.publish("purchaseReceipt", command);
    }
}
