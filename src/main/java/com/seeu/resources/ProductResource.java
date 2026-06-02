package com.seeu.resources;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/products")
public class ProductResource extends BaseResource {

    @GET
    public Response getAll() {
        return Response.ok().build();
    }

    @GET
    @Path("/{product_id}")
    public Response get(@PathParam("product_id") String productId) throws Exception {
        return Response.ok().build();
    }

    @PUT
    public Response save(String payload) throws Exception {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{product_id}")
    public Response delete(@PathParam("product_id") String productId) throws Exception {
        return Response.ok().build();
    }
}
