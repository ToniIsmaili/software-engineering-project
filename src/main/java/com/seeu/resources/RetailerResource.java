package com.seeu.resources;

import com.seeu.common.Responses;
import com.seeu.domains.Retailer;
import com.seeu.services.RetailerService;
import com.seeu.services.RetailerServiceImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/retailers")
public class RetailerResource extends BaseResource {
    RetailerService service = new RetailerServiceImpl();

    @GET
    public Response getAll() throws Exception {
        return Response.ok(toJson(service.getAll())).build();
    }

    @GET
    @Path("/{retailer_id}")
    public Response get(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        return Response.ok(toJson(service.get(retailerId))).build();
    }

    @PUT
    public Response save(String payload) throws Exception {
        Retailer retailer = fromJson(payload, Retailer.class);
        if (retailer.getId() == null) {
            retailer.setId(UUID.randomUUID().toString());
        }
        String validation = retailer.validate();
        if (validation != null) {
            throw new BadRequestException(validation);
        }
        service.save(retailer);
        return Response.ok(Responses.SAVE_SUCCESSFUL).build();
    }

    @DELETE
    @Path("/{retailer_id}")
    public Response delete(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        service.delete(retailerId);
        return Response.ok(Responses.DELETE_SUCCESSFUL).build();
    }
}
