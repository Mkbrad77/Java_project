package com.epita.data;


import com.epita.domain.entity.ProductEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class WarehouseRepository implements PanacheMongoRepositoryBase<ProductEntity, String> {
    /*private final Map<String, Integer> inventory = new HashMap<>();

    public Map<String, Integer> getAllProducts() {
        return inventory;
    }

    public Integer getProductQuantity(String name) {
        return inventory.get(name);
    }

    public void updateProductQuantity(String name, int quantity) {
        inventory.put(name, quantity);
    }
    public void clearInventory() {
        inventory.clear();
    }*/

    public ProductEntity findByName(String name) {
        return find("name", name).firstResult();
    }

    public void updateProductQuantity(String name, int quantity) {
        ProductEntity product = findByName(name);
        if (product != null) {
            product.setQuantity(quantity);
            update(product);
        }
    }

    public void restockProduct(String name, int quantity) {
        ProductEntity product = findByName(name);
        if (product != null) {
            product.setQuantity(product.getQuantity() + quantity);
            update(product);
        } else {
            persist(new ProductEntity(name, quantity));
        }
    }
}





