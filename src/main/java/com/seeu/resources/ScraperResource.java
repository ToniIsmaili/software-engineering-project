package com.seeu.resources;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/retailers/{retailer_id}/scraper")
public class ScraperResource extends BaseResource {

    @GET
    public Response get(@PathParam("retailer_id") String retailerId) throws Exception {
        return Response.ok().build();
    }

    @PUT
    public Response save(@PathParam("retailer_id") String retailerId, String payload) throws Exception {
        return Response.ok().build();
    }

    @DELETE
    public Response delete(@PathParam("retailer_id") String retailerId) throws Exception {
        return Response.ok().build();
    }
}
