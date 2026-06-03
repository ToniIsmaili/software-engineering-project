package com.seeu.resources;

import com.seeu.domains.Scraper;
import com.seeu.services.ScraperJobService;
import com.seeu.services.ScraperJobServiceImpl;
import com.seeu.services.ScraperService;
import com.seeu.services.ScraperServiceImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/retailers/{retailer_id}/scraper")
public class ScraperResource extends BaseResource {
    ScraperService scraperService = new ScraperServiceImpl();
    ScraperJobService scraperJobService = new ScraperJobServiceImpl();

    @GET
    public Response get(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        return Response.ok(toJson(scraperService.get(retailerId))).build();
    }

    @GET
    @Path("/jobs")
    public Response getJobs(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        return Response.ok(toJson(scraperJobService.getByRetailerId(retailerId))).build();
    }

    @POST
    @Path("/jobs/start")
    public Response startJob(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        scraperJobService.startJob(retailerId);
        return Response.ok().build();
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
        scraperService.save(scraper);
        return Response.ok("Scraper successfully saved.").build();
    }

    @DELETE
    public Response delete(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException("Invalid ID.");
        }
        scraperService.delete(retailerId);
        return Response.ok("Successfully deleted!").build();
    }
}
