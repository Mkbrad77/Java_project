package com.epita.domain.service;

import com.epita.data.CustomerRepository;
import com.epita.domain.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.io.IOException;
import java.util.*;


import com.epita.data.CustomerRepository;
import com.epita.domain.entity.CustomerEntity;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;

    @Inject
    PurchaseReceiptService purchaseReceiptService;

    @Inject
    PurchaseGraphService purchaseGraphService;

    @Inject
    @RestClient
    WarehouseServiceCustomer warehouseServiceCustomer;
    public Optional<CustomerEntity> getCustomerById(UUID id) throws IOException {
        List<PurchaseReceipt> purchase = purchaseReceiptService.searchPurchasesByUser(id);
        return Optional.of(new CustomerEntity(id, purchase.size()));
    }

    /*public boolean purchaseProducts(UUID customerId, List<ProductEntity> products) {
        //Response warehouseResponse = warehouseService.purchaseProducts(products);
        //if (warehouseResponse.getStatus() == 200) {
            CustomerEntity customer = customerRepository.getCustomerById(customerId)
                    .orElse(new CustomerEntity(customerId, 0));
            customer.setPurchases(customer.getPurchases() + 1);
            customerRepository.addOrUpdateCustomer(customer);
            return true;
        //}
        //return false;
    }*/

/* String validation = warehouseServiceCustomer.purchaseProducts(products).getStatusInfo().getReasonPhrase();
        if (!"the purchase is allowed to take place".equals(validation) && !"OK".equalsIgnoreCase(validation)) {
            return false;
        }*/
    public boolean purchaseProducts(UUID customerId, List<ProductEntity> products) throws IOException {
        Response warehouseResponse = warehouseServiceCustomer.purchaseProducts(products);
        if (warehouseResponse.getStatus() == 200) {
            CustomerEntity customer = customerRepository.getCustomerById(customerId)
                    .orElse(new CustomerEntity(customerId, 0));
            customer.setPurchases(customer.getPurchases() + 1);
            customerRepository.addOrUpdateCustomer(customer);

            // Create a new purchase receipt
            PurchaseReceipt receipt = new PurchaseReceipt(UUID.randomUUID(), customerId, products);
            purchaseReceiptService.indexPurchaseReceipt(receipt);

            // Update purchase graph
            for (ProductEntity product : products){
                purchaseGraphService.createOrUpdatePurchaseRelationship(customerId, product.getName(), product.getQuantity());
            }

            return true;
        }
        return false;
    }

    public List<PurchaseReceipt> searchPurchases(String user, String product) throws IOException {
        return purchaseReceiptService.searchReceipts(user, product);
    }

    public List<ProductPopularity> getProductPopularityByCustomers() {
        return purchaseGraphService.getProductPopularityByCustomers();
    }

    public List<CustomerPurchaseRank> getCustomersByProductPurchase(String productName) {
        return purchaseGraphService.getCustomersByProductPurchase(productName);
    }

}
