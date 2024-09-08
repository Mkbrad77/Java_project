package com.epita.domain.service;

import com.epita.domain.entity.ProductEntity;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api")
@RegisterRestClient(configKey="warehouse-service-api")

public interface WarehouseServiceCustomer {

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductEntity> getAllProducts();

    @POST
    @Path("/purchase")
    @Consumes(MediaType.APPLICATION_JSON)
    Response purchaseProducts(List<ProductEntity> products);
}