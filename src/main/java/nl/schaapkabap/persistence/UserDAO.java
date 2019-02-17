package nl.schaapkabap.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import nl.schaapkabap.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> implements DAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return list((Query<User>) namedQuery("User.findAll"));
    }

    public User findById(int id) {
        return get(id);
    }

    @SuppressWarnings("unchecked")
    public Optional<User> findUserByUsername(String username) {

        List<User> users = list((Query<User>) namedQuery("User.findByCredentials")
                .setParameter("username", username)
                .setMaxResults(1)
        );

        return Optional.ofNullable(users.size() > 0 ? users.get(0) : null);
    }

    @Override
    public void add(User obj) {
        this.currentSession().save(obj);
    }

    @Override
    public void update(User obj) {
        this.currentSession().merge(obj);
    }

    @Override
    public void delete(User obj) {
        this.currentSession().delete(obj);
    }
}
