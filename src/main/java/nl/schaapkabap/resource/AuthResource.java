package nl.schaapkabap.resource;


import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import nl.schaapkabap.views.View;
import nl.schaapkabap.models.User;
import nl.schaapkabap.persistence.UserDAO;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private UserDAO dao;

    @Inject
    public AuthResource(UserDAO dao) {
        this.dao = dao ;
    }

    @POST
    @UnitOfWork
    @Path("/login")
    @JsonView(View.Private.class)
    public User login(@FormDataParam("username") String username, @FormDataParam("password") String password) {
        Optional<User> user = this.dao.findUserByUsername(username);

        if ( ! user.isPresent())
            throw new NotFoundException();

        User auth = user.get();

        if (BCrypt.checkpw(password, auth.getPassword())) {
            return auth;
        } else {
            throw new NotFoundException();
        }
    }

}
