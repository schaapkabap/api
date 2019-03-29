package nl.schaapkabap.resource;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.schaapkabap.views.View;
import nl.schaapkabap.models.User;
import nl.schaapkabap.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService service;

    @Inject
    public UserResource(UserService service) {
        this.service = service;
    }

    @GET
    @RolesAllowed("user")
    @Path("/")
    @JsonView(View.Public.class)
    @UnitOfWork
    public List<User> all(@Auth User user) {
        return this.service.getAll(user);
    }

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    @JsonView(View.Public.class)
    @UnitOfWork
    public User show(@Auth User user, @PathParam("id") int id) {
        return this.service.get(user, id);
    }

    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User auth(@Auth User authenticator) {
        return authenticator;
    }

    @PUT
    @Path("/me")
    @JsonView(View.Private.class)
    @UnitOfWork
    public Response update(@Auth User auth, User user) {
        User originalUser = this.service.get(auth, user.getId());

        // Force rewrite roles
        user.setRoles(originalUser.getRoles());

        this.service.update(auth, user);
        return Response.ok().build();
    }
    @POST
    @RolesAllowed("user")
    @Consumes({MediaType.APPLICATION_JSON})
    @UnitOfWork
    public Response create(@Auth User auth, User user) {
        this.service.add(user);
        return Response.ok().build();
    }
}
