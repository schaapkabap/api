package nl.schaapkabap.auth;

import io.dropwizard.auth.Authorizer;
import nl.schaapkabap.models.User;

public class BasicAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User principal, String role) {
        return principal.hasRole(role);
    }

}
