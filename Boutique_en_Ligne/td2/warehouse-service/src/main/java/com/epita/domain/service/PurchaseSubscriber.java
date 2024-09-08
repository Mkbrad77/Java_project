package com.epita.domain.service;


import com.epita.data.PurchaseRepository;
import com.epita.data.WarehouseRepository;
import com.epita.domain.entity.PurchaseReceipt;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.vertx.core.Vertx;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Consumer;

@Startup
@ApplicationScoped
public class PurchaseSubscriber implements Consumer<PurchaseReceipt> {
    private final PubSubCommands.RedisSubscriber subscriber;

    @Inject
    WarehouseRepository repository;

    @Inject
    Vertx vertx;

    @Inject
    private WarehouseService warehouseService;

    @Inject
    PurchasePublisher purchasePublisher;

    @Inject
    PurchaseRepository purchaseRepository;  // Inject PurchaseRepository
    @Inject
    public PurchaseSubscriber(RedisDataSource ds) {
        subscriber = ds.pubsub(PurchaseReceipt.class).subscribe("PurchaseReceipt", this);
    }

    @Override
    public void accept(PurchaseReceipt command) {
        vertx.executeBlocking(future -> {
            String succeed = warehouseService.purchaseProducts(command.getProducts());
            PurchaseReceipt receipt = new PurchaseReceipt();
            receipt.setProducts(command.getProducts());
            receipt.setCustomerId(command.getCustomerId());
            receipt.setId(command.getId());
            if (succeed.contentEquals("ok"))
                receipt.succeed = true;
            //purchaseRepository.persist(receipt);  // Save the receipt in PurchaseRepository
            purchasePublisher.publish(receipt);
            future.complete();
        });
    }

    @PreDestroy
    public void destroy() {// terminate
        subscriber.unsubscribe();
    }
}


