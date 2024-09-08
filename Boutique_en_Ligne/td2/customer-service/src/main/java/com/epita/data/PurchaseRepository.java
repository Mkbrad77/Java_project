package com.epita.data;

import com.epita.domain.entity.PurchaseReceipt;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Getter
@Setter
public class PurchaseRepository implements PanacheMongoRepositoryBase<PurchaseReceipt, UUID> {

    public Optional<PurchaseReceipt> findPurchaseByCustomerIdAndPurchaseId(UUID customerId, UUID purchaseId) {
        System.out.println("Fetching purchase with ID: " + purchaseId + " for customer: " + customerId);
        return find("customerId = ?1 and id = ?2", customerId, purchaseId).firstResultOptional();
    }
}
