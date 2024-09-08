package com.epita.data;

import com.epita.domain.entity.CustomerEntity;
import jakarta.enterprise.context.ApplicationScoped;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@ApplicationScoped
public class CustomerRepository {
    private final Map<UUID, Integer> customerDatabase = new HashMap<>();

    public Optional<CustomerEntity> getCustomerById(UUID id) {
        if (customerDatabase.containsKey(id)) {
            return Optional.of(new CustomerEntity(id, customerDatabase.get(id)));
        }
        return Optional.empty();
    }

    public void addOrUpdateCustomer(CustomerEntity customer) {
        customerDatabase.put(customer.getId(), customer.getPurchases());
    }

}
