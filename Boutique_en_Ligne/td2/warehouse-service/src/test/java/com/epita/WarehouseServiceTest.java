package com.epita;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import com.epita.domain.entity.ProductEntity;
import com.epita.domain.service.WarehouseService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
/*
@QuarkusTest
public class WarehouseServiceTest {
    @Inject
    WarehouseService warehouseService;

    @BeforeEach
    void setup() {
        // Setup initial data for testing
        warehouseService.getWarehouseRepository().clearInventory();
        given()
                .body("[{\"name\": \"product1\", \"quantity\": 10}]")
                .header("Content-Type", "application/json")
                .when().post("/api/restock")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllProducts() {
        List<ProductEntity> products = warehouseService.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("product1", products.get(0).getName());
        assertEquals(10, products.get(0).getQuantity());
    }

    @Test
    void testGetProduct() {
        ProductEntity product = warehouseService.getProduct("product1");
        assertNotNull(product);
        assertEquals("product1", product.getName());
        assertEquals(10, product.getQuantity());
    }*/
    /*@Test
    void testGetProductByName() {
        ProductEntity product = warehouseService.getProductByName("product1");
        assertNotNull(product);
        assertEquals("product1", product.getName());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void testPurchaseProduct() {
        warehouseService.purchaseProducts(List.of(new ProductEntity("product1", 5)));
        assertEquals(5, warehouseService.getProductByName("product1").getQuantity());
    }

    @Test
    void testRestockProduct() {
        warehouseService.restockProducts(List.of(new ProductEntity("product1", 10)));
        assertEquals(20, warehouseService.getProductByName("product1").getQuantity());
    }

    @Test
    void testPurchaseProductNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            warehouseService.purchaseProducts(List.of(new ProductEntity("product2", 5)));
        });
    }

    @Test
    void testPurchaseMoreThanStock() {
        assertThrows(IllegalArgumentException.class, () -> {
            warehouseService.purchaseProducts(List.of(new ProductEntity("product1", 15)));
        });
    }

    @Test
    void testRestockNewProduct() {
        warehouseService.restockProducts(List.of(new ProductEntity("product2", 5)));
        assertEquals(5, warehouseService.getProductByName("product2").getQuantity());
    }*/
//}
