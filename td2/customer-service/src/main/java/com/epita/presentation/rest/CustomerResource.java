package com.epita.presentation.rest;

//import com.epita.config.AppConfig;
//import com.epita.domain.service.WarehouseService;
import com.epita.domain.entity.PurchaseReceipt;
import com.epita.domain.service.WarehouseServiceCustomer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.epita.domain.entity.CustomerEntity;
import com.epita.domain.entity.ProductEntity;
import com.epita.domain.service.CustomerService;
import org.jboss.logging.Logger;

//import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.*;
import java.util.stream.Collectors;

import com.epita.presentation.rest.WarehouseResource;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey="warehouse-service-api")
public class CustomerResource {

    @Inject
    CustomerService customerService;
    @Inject
    @RestClient
    WarehouseServiceCustomer warehouseResource;
    //AppConfig cong;
    @GET
    @Path("/products")
    /*public Response getAllProducts() {
        return Response.ok(warehouseResource.getAllProducts()).status(200, " the request succeeded").build();
        //return  warehouseResource.getAllProducts();//warehouseService.getAllProducts();
    }*/
    public Response getAllProducts() {
        return Response.ok(customerService.getAllProducts()).status(200).build();
    }

    @POST
    @Path("/purchase")
    public Response purchaseProducts(@HeaderParam("X-user-id") String userId, List<ProductEntity> products) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing user ID").build();
        }

        try {
            Response warehouseResponse;
            try {
                warehouseResponse = warehouseResource.purchaseProducts(products);
            } catch (WebApplicationException e) {
                int status = e.getResponse().getStatus();
                if (status == Response.Status.NOT_FOUND.getStatusCode()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
                } else if (status == Response.Status.BAD_REQUEST.getStatusCode()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Not enough stocks").build();
                }
                throw e;
            }

            if (warehouseResponse.getStatus() != 200) {
                return Response.status(warehouseResponse.getStatus()).entity(warehouseResponse.readEntity(String.class)).build();
            }

            UUID customerId = UUID.fromString(userId);
            boolean success = customerService.purchaseProducts(customerId, products);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid purchase request").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user ID").build();
        }
    }
    /*public Response purchaseProducts(@HeaderParam("X-user-id") String userId, List<ProductEntity> products) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing user ID").build();
        }

        try {
            Response warehouseResponse;
            try {
                warehouseResponse = warehouseResource.purchaseProducts(products);
            } catch (WebApplicationException e) {
                if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
                }
                throw e;
            }

            if (warehouseResponse.getStatus() != 200) {
                return Response.status(warehouseResponse.getStatus()).entity(warehouseResponse.readEntity(String.class)).build();
            }

            UUID customerId = UUID.fromString(userId);
            boolean success = customerService.purchaseProducts(customerId, products);
            if (success) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid purchase request").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user ID").build();
        }
    }*/

    /*@GET
    @Path("/purchases")
    public Response getPurchases(@HeaderParam("X-user-id") String userId) {
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
    }*/

    @GET
    @Path("/purchases")
    public Response getPurchases(@HeaderParam("X-user-id") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing user ID").build();
        }

        try {
            UUID customerId = UUID.fromString(userId);
            List<PurchaseReceipt> purchases = customerService.getPurchasesByCustomer(customerId);
            if (purchases.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No purchases found for customer").build();
            }

            Map<String, Object> responseBody = new LinkedHashMap<>();
            responseBody.put("customer", customerId);
            responseBody.put("purchases", purchases.stream().map(PurchaseReceipt::getId).collect(Collectors.toList()));
            responseBody.put("total", purchases.size());
            return Response.ok(responseBody).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user ID").build();
        }
    }

    @GET
    @Path("/purchases/{purchaseId}")
    public Response getPurchase(@HeaderParam("X-user-id") String userId, @PathParam("purchaseId") String purchaseId) {

        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing user ID").build();
        }
        try {
            UUID customerId = UUID.fromString(userId);
            UUID purchaseUUID = UUID.fromString(purchaseId);
            Optional<PurchaseReceipt> purchase = customerService.getPurchaseById(customerId, purchaseUUID);
            if (purchase.isPresent()) {
                return Response.ok(purchase.get().getProducts()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Purchase not found").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID format").build();
        }
    }

}

/*@POST
    @Path("/purchase")
    public Response purchaseProducts(@HeaderParam("X-user-id") String userId, List<ProductEntity> products) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Missing user ID").build();
        }

        try {
            UUID customerId = UUID.fromString(userId);
            Optional<CustomerEntity> customer = customerService.getCustomerById(customerId);
            if (customer.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            try {
                Response warehouseResponse = warehouseResource.purchaseProducts(products);
                if (warehouseResponse.getStatus() == 404) {
                    return Response.status(404).entity("Product not found or insufficient quantity").build();
                } else if (warehouseResponse.getStatus() != 200) {
                    return Response.status(warehouseResponse.getStatus()).entity(warehouseResponse.getEntity()).build();
                }

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
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user ID").build();
        }
    }*/