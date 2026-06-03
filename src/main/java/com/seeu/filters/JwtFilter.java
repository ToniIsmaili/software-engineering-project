package com.seeu.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.seeu.annotations.Secured;
import com.seeu.common.Responses;
import com.seeu.common.Utils;
import com.seeu.utils.JWTUtil;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String authHeader = containerRequestContext.getHeaderString("Authorization");

        if (Utils.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            abort(containerRequestContext);
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        try {
            DecodedJWT jwt = JWTUtil.validateToken(token);
            if (!"access".equals(jwt.getClaim("type").asString())) {
                abort(containerRequestContext);
            }
        } catch (Exception e) {
            abort(containerRequestContext);
        }
    }

    private void abort(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).entity(Responses.UNAUTHORIZED).build()
        );
    }
}
