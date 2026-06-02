package com.seeu.resources;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource extends BaseResource {

    @GET
    @Path("/{user_id}")
    public Response get(@PathParam("user_id") String userId) throws Exception {
        return Response.ok().build();
    }

    @PUT
    public Response save(String payload) throws Exception {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{user_id}")
    public Response delete(@PathParam("user_id") String userId) throws Exception {
        return Response.ok().build();
    }
}
