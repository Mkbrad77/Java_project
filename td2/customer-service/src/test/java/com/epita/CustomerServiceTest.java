package com.epita;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@QuarkusTest
public class CustomerServiceTest {

    /*private static final String BASE_URL = "http://localhost:8081/api";
    private static final String WAREHOUSE_URL = "http://localhost:8082/api"; // Assuming Warehouse Service runs on port 8080
    private UUID existingCustomerId;
    private UUID newCustomerId;

    @BeforeEach
    public void setup() {
        existingCustomerId = UUID.randomUUID();
        newCustomerId = UUID.randomUUID();

        // Restock the warehouse with initial products
        given()
                .body("[{\"name\": \"product1\", \"quantity\": 100}, {\"name\": \"product2\", \"quantity\": 50}]")
                .header("Content-Type", "application/json")
                .when().post(WAREHOUSE_URL + "/restock")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetAllProducts() {
        given()
                .when().get(BASE_URL + "/products")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .body("[0].name", not(blankOrNullString()))
                .body("[0].quantity", greaterThan(0));
    }

    @Test
    public void testPurchaseProducts_Successful() {
        String productName = "product1";
        int quantity = 1;

        given()
                .header("X-user-id", existingCustomerId.toString())
                .body("[{\"name\": \"" + productName + "\", \"quantity\": " + quantity + "}]")
                .header("Content-Type", "application/json")
                .when().post(BASE_URL + "/purchase")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPurchaseProducts_InvalidField() {
        String productName = "product1";
        int quantity = -1;

        given()
                .header("X-user-id", existingCustomerId.toString())
                .body("[{\"name\": \"" + productName + "\", \"quantity\": " + quantity + "}]")
                .header("Content-Type", "application/json")
                .when().post(BASE_URL + "/purchase")
                .then()
                .statusCode(400);
    }

    @Test
    public void testPurchaseProducts_ProductNotFound() {
        String productName = "nonexistentProduct";
        int quantity = 1;

        given()
                .header("X-user-id", existingCustomerId.toString())
                .body("[{\"name\": \"" + productName + "\", \"quantity\": " + quantity + "}]")
                .header("Content-Type", "application/json")
                .when().post(BASE_URL + "/purchase")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetCustomerPurchases_Successful() {
        given()
                .header("X-user-id", existingCustomerId.toString())
                .when().get(BASE_URL + "/purchases")
                .then()
                .statusCode(200)
                .body("customer", equalTo(existingCustomerId.toString()))
                .body("purchases", greaterThanOrEqualTo(0));
    }

    @Test
    public void testGetCustomerPurchases_InvalidField() {
        given()
                .header("X-user-id", "")
                .when().get(BASE_URL + "/purchases")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetCustomerPurchases_CustomerNotFound() {
        given()
                .header("X-user-id", newCustomerId.toString())
                .when().get(BASE_URL + "/purchases")
                .then()
                .statusCode(404);
    }*/
}
