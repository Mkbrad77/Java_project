package com.epita.domain.service;

import com.epita.domain.entity.ProductEntity;
import com.epita.domain.entity.PurchaseReceipt;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/api")
@RegisterRestClient(configKey="warehouse-service-api")

public interface WarehouseServiceCustomer {

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductEntity> getAllProducts();
    @GET
    @Path("/product/{name}")
    Response getProduct(@PathParam("name") String name);

    @POST
    @Path("/purchase")
    @Consumes(MediaType.APPLICATION_JSON)
    Response purchaseProducts(List<ProductEntity> products);
}