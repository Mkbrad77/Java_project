package com.epita.presentation.rest;

//import com.epita.config.AppConfig;
//import com.epita.domain.service.WarehouseService;
import com.epita.domain.entity.*;
import com.epita.domain.service.PurchaseGraphService;
import com.epita.domain.service.PurchaseReceiptService;
import com.epita.domain.service.WarehouseServiceCustomer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.epita.domain.service.CustomerService;

//import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.util.*;

import com.epita.presentation.rest.WarehouseResource;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey="warehouse-service-api")
public class CustomerResource {

    @Inject
    CustomerService customerService;
    //@Inject
    @RestClient
    WarehouseServiceCustomer warehouseResource;
    //AppConfig cong;
    @GET
    @Path("/products")
    public Response getAllProducts() {
        return Response.ok(warehouseResource.getAllProducts()).status(200, " the request succeeded").build();
        //return  warehouseResource.getAllProducts();//warehouseService.getAllProducts();
    }

    @POST
    @Path("/purchase")
    public Response purchaseProducts(@HeaderParam("X-user-id") String userId, List<ProductEntity> products) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Missing user ID").build();
        }

        //try {
            UUID customerId = UUID.fromString(userId);
            //Optional<CustomerEntity> customer = customerService.getCustomerById(customerId);
            //if (customer.isEmpty()) {
              //  return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            //}
            try {
                /*Response warehouseResponse = warehouseResource.purchaseProducts(products);
                if (warehouseResponse.getStatus() == 404) {
                    return Response.status(404).entity("Product not found or insufficient quantity").build();
                } else if (warehouseResponse.getStatus() != 200) {
                    return Response.status(warehouseResponse.getStatus()).entity(warehouseResponse.getEntity()).build();
                }*/



                // a voir en cas de doute sur des valeur de retour de test de cas d'erreur



                boolean success = customerService.purchaseProducts(customerId, products);
                if (success) {
                    return Response.ok().build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            } catch (jakarta.ws.rs.WebApplicationException e) {
                if (e.getResponse().getStatus() == 404) {
                    return Response.status(404).entity("Product not found or insufficient quantity").build();
                }
                return Response.status(e.getResponse().getStatus()).entity(e.getResponse().getEntity()).build();
                //throw e;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        // } catch (IllegalArgumentException e) {
        //    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user ID").build();
        //}
    }

    @GET
    @Path("/purchases")
    public Response getPurchases(@HeaderParam("X-user-id") String userId) throws IOException {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing user ID").build();
        }

        try {
            UUID customerId = UUID.fromString(userId);
            Optional<CustomerEntity> customer = customerService.getCustomerById(customerId);
            if (customer.isPresent()) {
                Map<String, Object> responseBody = new LinkedHashMap<>();
                responseBody.put("customer", customer.get().getId());
                responseBody.put("purchases", customer.get().getPurchases());
                return Response.ok(responseBody).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user ID").build();
        }
    }

    @GET
    @Path("/purchases/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPurchases(@QueryParam("user") String user, @QueryParam("product") String product) {
        try {
            if ((user == null || user.isBlank()) && (product == null || product.isBlank())) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            List<PurchaseReceipt> purchases = customerService.searchPurchases(user, product);
            //List<PurchaseReceipt> purchases = customerService.searchPurchases(user, product);

            // Transformer la liste de PurchaseReceipt en la structure désirée
            List<Map<String, Object>> response = purchases.stream().map(receipt -> {
                Map<String, Object> receiptMap = new HashMap<>();
                receiptMap.put("userId", receipt.getCustomerId());
                receiptMap.put("purchaseId", receipt.getPurchaseId());
                List<Map<String, Object>> productList = receipt.getProducts().stream().map(prod -> {
                    Map<String, Object> productMap = new HashMap<>();
                    productMap.put("name", prod.getName());
                    productMap.put("quantity", prod.getQuantity());
                    return productMap;
                }).toList();
                receiptMap.put("products", productList);
                return receiptMap;
            }).toList();

            return Response.ok(response).build();
            //return Response.ok(purchases).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/popularity/products/bycustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductPopularity() {
        List<ProductPopularity> popularity = customerService.getProductPopularityByCustomers();
        return Response.ok(popularity).build();
    }

    @GET
    @Path("/popularity/customers/bypurchasesof/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersByProduct(@PathParam("productName") String productName) {
        try {
            List<CustomerPurchaseRank> customers = customerService.getCustomersByProductPurchase(productName);
            return Response.ok(customers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}

