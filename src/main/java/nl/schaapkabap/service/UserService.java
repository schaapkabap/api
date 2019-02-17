package nl.schaapkabap.service;

import nl.schaapkabap.models.User;
import nl.schaapkabap.persistence.DAO;

public class UserService extends Service<User> {

    public UserService(DAO<User> dao) {
        super(dao);
    }

    @Override
    boolean hasAccess(User user, User obj) {
        return user.equals(obj);
    }

}
