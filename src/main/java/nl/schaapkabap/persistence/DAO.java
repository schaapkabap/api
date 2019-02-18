package nl.schaapkabap.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class DAO<T> extends AbstractDAO<T> {

    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public DAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public abstract List<T> getAll();

    public abstract T findById(int id);

    public abstract void add(T obj);

    public abstract void update(T obj);

    public abstract void delete(T obj);

}
