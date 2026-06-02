package com.seeu.resources;

import com.seeu.domains.Scraper;
import com.seeu.services.ScraperService;
import com.seeu.services.ScraperServiceImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/retailers/{retailer_id}/scraper")
public class ScraperResource extends BaseResource {
    ScraperService service = new ScraperServiceImpl();

    @GET
    public Response get(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        return Response.ok(toJson(service.get(retailerId))).build();
    }

    @PUT
    public Response save(@PathParam("retailer_id") String retailerId, String payload) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        Scraper scraper = fromJson(payload, Scraper.class);
        scraper.setRetailerId(retailerId);
        if (scraper.getId() == null || scraper.getId().isEmpty()) {
            scraper.setId(UUID.randomUUID().toString());
        }
        String validate = scraper.validate();
        if (validate != null) {
            throw new BadRequestException(validate);
        }
        service.save(scraper);
        return Response.ok("Scraper successfully saved.").build();
    }

    @DELETE
    public Response delete(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        service.delete(retailerId);
        return Response.ok("Successfully deleted!").build();
    }
}
