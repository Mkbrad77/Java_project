package com.epita.presentation.rest;

//import com.epita.config.AppConfig;
//import com.epita.domain.service.WarehouseService;
import com.epita.domain.service.WarehouseServiceCustomer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.epita.domain.entity.CustomerEntity;
import com.epita.domain.entity.ProductEntity;
import com.epita.domain.service.CustomerService;

//import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

        try {
            UUID customerId = UUID.fromString(userId);
            //Optional<CustomerEntity> customer = customerService.getCustomerById(customerId);
            //if (customer.isEmpty()) {
              //  return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            //}
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
    }

    @GET
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
    }
}

