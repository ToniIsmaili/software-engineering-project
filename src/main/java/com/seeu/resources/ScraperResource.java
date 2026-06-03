package com.seeu.resources;

import com.seeu.common.Responses;
import com.seeu.domains.Scraper;
import com.seeu.domains.ScraperJob;
import com.seeu.domains.ScraperLog;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/retailers/{retailer_id}/scraper")
public class ScraperResource extends BaseResource {
    ScraperService scraperService = new ScraperServiceImpl();
    ScraperJobService scraperJobService = new ScraperJobServiceImpl();

    @GET
    public Response get(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        return Response.ok(toJson(scraperService.get(retailerId))).build();
    }

    @GET
    @Path("/jobs")
    public Response getJobs(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        return Response.ok(toJson(scraperJobService.getByRetailerId(retailerId))).build();
    }

    @POST
    @Path("/jobs/start")
    public Response startJob(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        scraperJobService.startJob(retailerId);
        return Response.ok(Responses.STARTED_JOB).build();
    }

    @PUT
    @Path("/jobs/{job_id}/end")
    public Response endJob(@PathParam("retailer_id") String retailerId,
                           @PathParam("job_id") String jobId,
                           @QueryParam("status") String status) throws Exception {
        if (retailerId == null || retailerId.isEmpty() || jobId == null || jobId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        if (status == null || status.isEmpty() || ScraperJob.Status.RUNNING.name().equals(status)) {
            throw new BadRequestException(Responses.INVALID_VALUE);
        }
        try {
            ScraperJob.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Responses.INVALID_VALUE);
        }
        scraperJobService.endJob(retailerId, jobId, status);
        return Response.ok(Responses.ENDED_JOB).build();
    }

    @GET
    @Path("/jobs/{job_id}/logs")
    public Response getLogs(@PathParam("retailer_id") String retailerId,
                            @PathParam("job_id") String jobId) throws Exception {
        if (jobId == null || jobId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        return Response.ok(toJson(scraperJobService.getLogs(jobId))).build();
    }

    @POST
    @Path("/jobs/{job_id}/logs")
    public Response addLog(@PathParam("retailer_id") String retailerId,
                           @PathParam("job_id") String jobId,
                           String payload) throws Exception {
        if (jobId == null || jobId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        ScraperLog scraperLog = fromJson(payload, ScraperLog.class);
        scraperLog.setScraperJobId(jobId);
        scraperLog.setId(UUID.randomUUID().toString());
        scraperJobService.addLog(scraperLog);
        return Response.ok(Responses.SAVE_SUCCESSFUL).build();
    }

    @PUT
    public Response save(@PathParam("retailer_id") String retailerId, String payload) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
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
        return Response.ok(Responses.SAVE_SUCCESSFUL).build();
    }

    @DELETE
    public Response delete(@PathParam("retailer_id") String retailerId) throws Exception {
        if (retailerId == null || retailerId.isEmpty()) {
            throw new BadRequestException(Responses.INVALID_ID);
        }
        scraperService.delete(retailerId);
        return Response.ok(Responses.DELETE_SUCCESSFUL).build();
    }
}
