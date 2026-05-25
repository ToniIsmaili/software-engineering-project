package com.seeu.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/products")
public class ProductResource extends BaseResource {

    @GET
    public Response getAll() {
        return Response.ok().build();
    }
}
