package com.epita;
import com.epita.domain.entity.ProductEntity;
import com.epita.domain.service.WarehouseService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WarehouseTest {
   /* @Inject
    WarehouseService warehouseService;

    @BeforeEach
    void setup() {
        // Setup initial data for testing
        given()
                .body("[{\"name\": \"product1\", \"quantity\": 10}]")
                .header("Content-Type", "application/json")
                .when().post("/api/restock")
                .then()
                .statusCode(200);
    }
    @Test
    public void testGetAllProducts() {
        given()
                .when().get("/api/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPurchaseProducts() {
        ProductEntity product = new ProductEntity("Product1", 5);
        List<ProductEntity> products = List.of(product);

        given()
                .contentType(ContentType.JSON)
                .body(products)
                .when().post("/api/purchase")
                .then()
                .statusCode(404); // Since there's no product initially, purchase should fail
    }*/
}
