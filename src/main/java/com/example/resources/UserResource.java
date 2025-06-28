package com.example.resources;

import com.example.api.User;
import com.example.dao.UserDAO;
import com.codahale.metrics.annotation.Timed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private final Jdbi jdbi;

    public UserResource(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @GET
    @Timed
    public List<User> getAllUsers() {
        return jdbi.withExtension(UserDAO.class, UserDAO::getAllUsers);
    }

    @GET
    @Path("/{id}")
    @Timed
    public User getUserById(@PathParam("id") Long id) {
        User user = jdbi.withExtension(UserDAO.class, dao -> dao.getUserById(id));
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
        return user;
    }

    @POST
    @Timed
    public User createUser(User user) {
        Long id = jdbi.withExtension(UserDAO.class, dao -> dao.createUser(user));
        user.setId(id);
        return user;
    }

    @PUT
    @Path("/{id}")
    @Timed
    public User updateUser(@PathParam("id") Long id, User user) {
        user.setId(id);
        jdbi.useExtension(UserDAO.class, dao -> dao.updateUser(user));
        return user;
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public Response deleteUser(@PathParam("id") Long id) {
        jdbi.useExtension(UserDAO.class, dao -> dao.deleteUser(id));
        return Response.noContent().build();
    }
} 