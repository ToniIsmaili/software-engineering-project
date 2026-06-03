package com.seeu.resources;

import com.seeu.common.Responses;
import com.seeu.common.Utils;
import com.seeu.domains.User;
import com.seeu.services.UserService;
import com.seeu.services.UserServiceImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/users")
public class UserResource extends BaseResource {
    UserService service = new UserServiceImpl();

    @GET
    @Path("/{user_id}")
    public Response get(@PathParam("user_id") String userId) throws Exception {
        validateIds(userId);
        return Response.ok(toJson(service.get(userId))).build();
    }

    @PUT
    public Response save(String payload) throws Exception {
        User user = fromJson(payload, User.class);
        if (Utils.isNullOrEmpty(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        String validate = user.validate();
        if (validate != null) {
            throw new BadRequestException(validate);
        }
        service.save(user);
        return Response.ok(Responses.SAVE_SUCCESSFUL).build();
    }

    @DELETE
    @Path("/{user_id}")
    public Response delete(@PathParam("user_id") String userId) throws Exception {
        validateIds(userId);
        service.delete(userId);
        return Response.ok(Responses.DELETE_SUCCESSFUL).build();
    }
}
