package com.epita.data;

import com.epita.domain.entity.CustomerEntity;
import com.epita.domain.entity.PurchaseReceipt;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ApplicationScoped
public class CustomerRepository implements PanacheMongoRepositoryBase<CustomerEntity, UUID> {
   /* private final Map<UUID, Integer> customerDatabase = new HashMap<>();

    public Optional<CustomerEntity> getCustomerById(UUID id) {
        if (customerDatabase.containsKey(id)) {
            return Optional.of(new CustomerEntity(id, customerDatabase.get(id)));
        }
        return Optional.empty();
    }

    public void addOrUpdateCustomer(CustomerEntity customer) {
        customerDatabase.put(customer.getId(), customer.getPurchases());
    }*/

    //public Optional<PurchaseReceipt> findPurchaseByCustomerIdAndPurchaseId(UUID customerId, UUID purchaseId) {
        //return find("customerId = ?1 and id = ?2", customerId, purchaseId).firstResultOptional();
    //}

    /*public Optional<PurchaseReceipt> findPurchaseByCustomerIdAndPurchaseId(UUID customerId, UUID purchaseId) {
        List<PurchaseReceipt> results = list("customerId = ?1 and id = ?2", customerId, purchaseId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }*/



}
