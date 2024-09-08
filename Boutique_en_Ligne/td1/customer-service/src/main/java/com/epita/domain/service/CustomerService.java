package com.epita.domain.service;

import com.epita.data.CustomerRepository;
import com.epita.domain.entity.CustomerEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.List;
import java.util.Optional;


import com.epita.data.CustomerRepository;
import com.epita.domain.entity.CustomerEntity;
import com.epita.domain.entity.ProductEntity;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;

    public Optional<CustomerEntity> getCustomerById(UUID id) {
        return customerRepository.getCustomerById(id);
    }

    public boolean purchaseProducts(UUID customerId, List<ProductEntity> products) {
        //Response warehouseResponse = warehouseService.purchaseProducts(products);
        //if (warehouseResponse.getStatus() == 200) {
            CustomerEntity customer = customerRepository.getCustomerById(customerId)
                    .orElse(new CustomerEntity(customerId, 0));
            customer.setPurchases(customer.getPurchases() + 1);
            customerRepository.addOrUpdateCustomer(customer);
            return true;
        //}
        //return false;
    }
}
