package nl.schaapkabap.auth;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import nl.schaapkabap.models.User;
import nl.schaapkabap.persistence.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {


    private UserDAO dao;

    public BasicAuthenticator(UserDAO dao) {
        this.dao = dao;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

        Optional<User> user = this.dao.findUserByUsername(credentials.getUsername());

        // If there is no user found, give back an empty user
        if (!user.isPresent()) return Optional.empty();

        else {
            if (BCrypt.checkpw(credentials.getPassword(), user.get().getPassword())) {
                return user;
            } else {
                return Optional.empty();
            }
        }


    }

}
