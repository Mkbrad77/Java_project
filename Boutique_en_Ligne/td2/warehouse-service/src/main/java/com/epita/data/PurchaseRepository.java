package com.epita.data;


import com.epita.domain.entity.PurchaseReceipt;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PurchaseRepository implements PanacheMongoRepositoryBase<PurchaseReceipt, UUID> {
    public void saveReceipt(PurchaseReceipt receipt) {
        persist(receipt);
    }
    public Optional<PurchaseReceipt> findPurchaseByCustomerIdAndPurchaseId(UUID customerId, UUID purchaseId) {
        List<PurchaseReceipt> results = list("customerId = ?1 and id = ?2", customerId, purchaseId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
