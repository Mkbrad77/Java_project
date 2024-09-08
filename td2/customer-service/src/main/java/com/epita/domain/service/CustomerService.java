/*package com.epita.domain.service;

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
}*/
package com.epita.domain.service;

import com.epita.data.CustomerRepository;
import com.epita.data.PurchaseRepository;
import com.epita.domain.entity.CustomerEntity;
import com.epita.domain.entity.ProductEntity;
import com.epita.domain.entity.PurchaseReceipt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;

    @Inject
    PurchasePublisher publisher;

    @Inject
    PurchaseRepository purchaseRepository;

    @RestClient
    WarehouseServiceCustomer warehouseServiceCustomer;

    public List<ProductEntity> getAllProducts() {
        return warehouseServiceCustomer.getAllProducts();
    }
    public List<PurchaseReceipt> getPurchasesByCustomer(UUID customerId) {
        return purchaseRepository.list("customerId", customerId);
    }

    public Optional<PurchaseReceipt> getPurchaseById(UUID customerId, UUID purchaseId) {
        // Étape 1 : Obtenir toutes les purchases du client
        List<PurchaseReceipt> purchases = getPurchasesByCustomer(customerId);

        // Étape 2 : Trouver le PurchaseReceipt avec l'ID donné
        return purchases.stream()
                .filter(purchase -> purchase.getId().equals(purchaseId))
                .findFirst();
    }
    public boolean purchaseProducts(UUID customerId, List<ProductEntity> products) {
        for (ProductEntity product : products) {
            Response warehouseProductResponse = warehouseServiceCustomer.getProduct(product.getName());
            if (warehouseProductResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return false; // Invalid purchase
            }
        }

        Optional<CustomerEntity> customerOptional = customerRepository.findByIdOptional(customerId);
        CustomerEntity customer;
        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
        } else {
            customer = new CustomerEntity(customerId, 0);
            customerRepository.persist(customer);
        }
        customer.setPurchases(customer.getPurchases() + 1);
        customerRepository.persistOrUpdate(customer);

        PurchaseReceipt receipt = new PurchaseReceipt(customerId, products);
        purchaseRepository.persistOrUpdate(receipt);
        publisher.publish(receipt);
        return true;
    }

    public void handlePurchaseResult(PurchaseReceipt receipt) {
        if (!receipt.isSucceed()) {
            customerRepository.delete("customerId = ?1 and id = ?2", receipt.getCustomerId(), receipt.getId());
        }
    }

}
