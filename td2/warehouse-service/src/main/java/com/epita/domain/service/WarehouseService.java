/*package com.epita.domain.service;

import com.epita.data.WarehouseRepository;
import com.epita.domain.entity.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RegisterRestClient
@Getter
@ApplicationScoped
public class WarehouseService {

    @Inject
    WarehouseRepository warehouseRepository;


    public List<ProductEntity> getAllProducts() {
        Map<String, Integer> inventory = warehouseRepository.getAllProducts();
        return inventory.entrySet().stream()
                .map(entry -> new ProductEntity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public String testProduct(List<ProductEntity> products) {
        for (ProductEntity product : products) {
            if (product.getName() == null || product.getName().isBlank()) {
                return "empty or missing name";//Response.status(400, "empty or missing name").build();
            } else if (product.getQuantity() <= 0) {
                return "zero or negative quantity";//Response.status(400, "zero or negative quantity").build();
            }
            Integer existingProduct = warehouseRepository.getProductQuantity(product.getName());
            if (existingProduct == null) {
                return "no product matches " + product.getName();//Response.status(404, "no product matches " + product.getName()).build();
            }

            if (existingProduct < product.getQuantity()) {
                return "not enough stocks";//Response.status(400, "not enough stocks").build();
            }
        }
        return "ok";
    }
    public ProductEntity getProduct(String name) {
        Integer quantity = warehouseRepository.getProductQuantity(name);
        if (quantity == null) {
            return null;
        }
        return new ProductEntity(name, quantity);
    }

    public boolean purchaseProducts(List<ProductEntity> products) {
        String validationResult = testProduct(products);
        if (!"ok".equals(validationResult)) {
            return false;
        }
        for (ProductEntity product : products) {
            String name = product.getName();
            int quantity = product.getQuantity();
            Integer currentQuantity = warehouseRepository.getProductQuantity(name);

            if (currentQuantity == null || currentQuantity < quantity) {
                return false;
            }
        }

        for (ProductEntity product : products) {
            String name = product.getName();
            int quantity = product.getQuantity();
            int newQuantity = warehouseRepository.getProductQuantity(name) - quantity;
            warehouseRepository.updateProductQuantity(name, newQuantity);
        }

        return true;
    }

    public void restockProducts(List<ProductEntity> products) {
        for (ProductEntity product : products) {
            String name = product.getName();
            int quantity = product.getQuantity();
            Integer currentQuantity = warehouseRepository.getProductQuantity(name);

            int newQuantity = (currentQuantity == null ? 0 : currentQuantity) + quantity;
            warehouseRepository.updateProductQuantity(name, newQuantity);
        }
    }
}
*/

package com.epita.domain.service;

import com.epita.data.PurchaseRepository;
import com.epita.data.WarehouseRepository;
import com.epita.domain.entity.ProductEntity;
import com.epita.domain.entity.PurchaseReceipt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@RegisterRestClient
@Getter
@ApplicationScoped
public class WarehouseService {
    @Inject
    WarehouseRepository warehouseRepository;

    @Inject
    PurchaseRepository purchaseRepository;


    public List<ProductEntity> getAllProducts() {
        return warehouseRepository.listAll();
    }

    public ProductEntity getProduct(String name) {
        return warehouseRepository.findById(name);
    }

    public String purchaseProducts(List<ProductEntity> products) {
        for (ProductEntity product : products) {
            String name = product.getName();
            int quantity = product.getQuantity();
            ProductEntity existingProduct = warehouseRepository.findById(name);

            if (existingProduct == null) {
                return "No product matches name";
            } else if (existingProduct.getQuantity() < quantity) {
                return "Not enough stocks";
            }

            existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
            warehouseRepository.persistOrUpdate(existingProduct);
        }
        return "ok";
    }

    public void processReceipt(UUID customerId, List<ProductEntity> products) {
        PurchaseReceipt receipt = new PurchaseReceipt();
        receipt.setId(UUID.randomUUID());
        receipt.setCustomerId(customerId);
        receipt.setProducts(products);
        purchaseRepository.saveReceipt(receipt);
    }

    /*public void restockProducts(List<ProductEntity> products) {
        for (ProductEntity product : products) {
            ProductEntity existingProduct = warehouseRepository.findById(product.getName());
            if (existingProduct == null) {
                warehouseRepository.persist(product);
            } else {
                existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
                warehouseRepository.update(existingProduct);
            }
        }
    }*/
    public void restockProducts(List<ProductEntity> products) {
        for (ProductEntity product : products) {
            warehouseRepository.restockProduct(product.getName(), product.getQuantity());
        }
    }
}
