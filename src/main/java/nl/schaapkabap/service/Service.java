package nl.schaapkabap.service;

import nl.schaapkabap.models.User;
import nl.schaapkabap.persistence.DAO;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Service<T> {

    protected final DAO<T> dao;

    public Service(DAO<T> dao) {
        this.dao = dao;
    }

    abstract boolean hasAccess(User user, T obj);

    public List<T> getAll(User user) {
        List<T> objs = this.dao.getAll();

        return objs
                .stream()
                .filter(o -> this.hasAccess(user, o))
                .collect(Collectors.toList())
                ;
    }

    public T get(User user, int id) {
        T obj = this.dao.findById(id);

        this.requireResult(obj);

        if (!hasAccess(user, obj))
            throw new WebApplicationException(Response.Status.FORBIDDEN);

        return obj;
    }

    public void add(T obj) {
        this.dao.add(obj);
    }

    public void update(User user, T obj) {
        if (!hasAccess(user, obj))
            throw new WebApplicationException(Response.Status.FORBIDDEN);

        this.dao.update(obj);

    }

    public void delete(User user, int id) {
        this.dao.delete(this.get(user, id));
    }

    private boolean requireResult(T obj) {
        if (obj == null) {
            throw new NotFoundException();
        }

        return true;
    }

}
