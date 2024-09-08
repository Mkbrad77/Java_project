package com.epita.domain.service;

import com.epita.data.CustomerRepository;
import com.epita.domain.entity.PurchaseReceipt;
import com.epita.domain.service.CustomerService;
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
    Vertx vertx;

    @Inject
    private CustomerService customerService;

    @Inject
    public PurchaseSubscriber(RedisDataSource ds) {
        subscriber = ds.pubsub(PurchaseReceipt.class).subscribe("PurchaseReceipt", this);
    }

    @Override
    public void accept(PurchaseReceipt purchaseReceipt) {
        vertx.executeBlocking(future -> {
            customerService.handlePurchaseResult(purchaseReceipt);
            future.complete();
        });
    }

    @PreDestroy
    public void destroy() {// terminate
        subscriber.unsubscribe();
    }
}

