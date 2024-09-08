package com.epita.data;


import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class WarehouseRepository {
    private final Map<String, Integer> inventory = new HashMap<>();

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
    }
}
