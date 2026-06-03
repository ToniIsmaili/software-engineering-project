package com.seeu.resources;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.seeu.annotations.Secured;
import com.seeu.common.Responses;
import com.seeu.domains.Credentials;
import com.seeu.domains.JWTToken;
import com.seeu.domains.User;
import com.seeu.services.AuthService;
import com.seeu.services.AuthServiceImpl;
import com.seeu.services.UserService;
import com.seeu.services.UserServiceImpl;
import com.seeu.utils.JWTUtil;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource extends BaseResource {
    final AuthService service = new AuthServiceImpl();
    final UserService userService = new UserServiceImpl();

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
    @Path("/signup")
    public Response signUp(String payload) throws Exception {
        Credentials credentials = fromJson(payload, Credentials.class);
        String message = credentials.validate();
        if (message != null) {
            throw new BadRequestException(message);
        }
        User user = userService.signUp(credentials);
        if (user == null) {
            return Response.status(Response.Status.CONFLICT).entity(Responses.EMAIL_ALREADY_EXISTS).build();
        }
        return Response.status(Response.Status.CREATED).entity(toJson(user)).build();
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

    @GET
    @Path("/me")
    @Secured
    public Response me(@Context HttpHeaders headers) throws Exception {
        String authHeader = headers.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Responses.UNAUTHORIZED).build();
        }
        DecodedJWT jwt = JWTUtil.validateToken(authHeader.substring("Bearer ".length()));
        User user = userService.getByEmail(jwt.getSubject());
        return Response.ok(toJson(user)).build();
    }
}
