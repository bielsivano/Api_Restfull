package com.br.users;
import com.br.users.dto.CreateUserRequest;
import com.br.users.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkussocial.domain.model.Users;

import java.util.Set;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private final Validator validator;

    @Inject
    public UsersResource(Validator validator) {

        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if (!violations.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return Response.status(400).entity(responseError).build();
        }

        Users user = new Users();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());

        user.persist();

        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }
    @GET
    public Response ListAllUsers(){
        PanacheQuery<PanacheEntityBase>query = Users.findAll();
        return Response.ok(query.list()).build();
    }
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUsers(@PathParam("id")long id) {
        Users user = Users.findById(id);

        if (user != null) {
            user.delete();
            return Response.noContent().build();

        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUsers(@PathParam("id")long id, CreateUserRequest userData){
        Users user = Users.findById(id);
        if (user != null){
            user.setName(userData.getName());

            user.setAge(userData.getAge());

            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();

    }
}