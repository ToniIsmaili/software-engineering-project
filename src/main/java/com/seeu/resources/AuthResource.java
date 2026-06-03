package com.seeu.resources;

import com.seeu.common.Responses;
import com.seeu.domains.Credentials;
import com.seeu.domains.JWTToken;
import com.seeu.services.AuthService;
import com.seeu.services.AuthServiceImpl;
import com.seeu.utils.JWTUtil;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource extends BaseResource {
    final AuthService service = new AuthServiceImpl();

    @POST
    @Path("/login")
    public Response login(String payload) throws Exception {
        Credentials credentials = fromJson(payload, Credentials.class);
        String message = credentials.validate();
        if (message != null) {
            throw new BadRequestException(message);
        }
        if (!service.authenticate(credentials)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(toJson(new JWTToken(
                JWTUtil.generateAccessToken(credentials.getEmail()),
                JWTUtil.generateRefreshToken(credentials.getEmail())
        ))).build();
    }

    @POST
    @Path("/refresh")
    public Response refresh(String payload) {
        JWTToken token = fromJson(payload, JWTToken.class);
        try {
            return Response.ok(toJson(JWTUtil.refreshToken(token.getRefreshToken()))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Responses.UNAUTHORIZED).build();
        }
    }
}
