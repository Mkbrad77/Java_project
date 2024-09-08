package com.epita.presentation.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import com.epita.domain.entity.ProductEntity;
import com.epita.domain.service.WarehouseService;

import jakarta.inject.Inject;//javax.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.List;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey="warehouse-service-api")
public class WarehouseResource {

    @Inject
    WarehouseService warehouseService;

    @GET
    @Path("/products")
    public Response getAllProducts() {
        return Response.ok(warehouseService.getAllProducts()).status(200, " the request succeeded").build();
    }

    @GET
    @Path("/product/{name}")
    public Response getProduct(@PathParam("name") String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            return Response.status(400, "invalid field").build();
        }

        ProductEntity product = warehouseService.getProduct(name);
        if (product == null) {
            return Response.status(404, "no product matches " + name).build();
        }

        return Response.ok(product).build();
    }

    @POST
    @Path("/purchase")
    public Response purchaseProducts(List<ProductEntity> products) {
        if (products == null || products.isEmpty() || products.stream().anyMatch(p -> p.getName() == null || p.getName().isBlank() || p.getQuantity() <= 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String ifValidityProduct =  warehouseService.testProduct(products);
        if (ifValidityProduct.startsWith("empty or missing name"))
            return Response.status(400, "empty or missing name").build();
        else if (ifValidityProduct.startsWith("zero or negative quantity")) {
            return Response.status(400, "zero or negative quantity").build();
        } else if (ifValidityProduct.startsWith("no product matches")) {
            return Response.status(404, ifValidityProduct).build();
        } else if (ifValidityProduct.startsWith("not enough stocks")) {
            return Response.status(400, "not enough stocks").build();
        }
        boolean success = warehouseService.purchaseProducts(products);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().status(200, "the purchase is allowed to take place").build();
    }

    @POST
    @Path("/restock")
    public Response restockProducts(List<ProductEntity> products) {
        if (products == null || products.isEmpty()){ //|| products.stream().anyMatch(p -> p.getName() == null || p.getName().isBlank() || p.getQuantity() <= 0)) {
            return Response.status(400, "empty or not valid list").build();
        }
        for (ProductEntity product : products) {
            if (product.getName() == null || product.getName().isBlank()) {
                return Response.status(400, "empty or missing name").build();
            } else if (product.getQuantity() <= 0) {
                return Response.status(400, "zero or negative quantity").build();
            }
        }

        warehouseService.restockProducts(products);
        return Response.ok().build();
    }
}

