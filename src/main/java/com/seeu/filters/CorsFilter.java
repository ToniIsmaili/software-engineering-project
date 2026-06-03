package com.seeu.filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION - 100)
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    private static final String MAX_AGE = "Access-Control-Max-Age";

    @Override
    public void filter(ContainerRequestContext request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            request.abortWith(Response.ok().build());
        }
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        response.getHeaders().putSingle(ALLOW_ORIGIN, "*");
        response.getHeaders().putSingle(ALLOW_HEADERS, "*");
        response.getHeaders().putSingle(ALLOW_METHODS, "*");
        response.getHeaders().putSingle(EXPOSE_HEADERS, "*");
        response.getHeaders().putSingle(MAX_AGE, "86400");
    }
}
