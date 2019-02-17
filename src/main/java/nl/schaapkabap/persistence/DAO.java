package nl.schaapkabap.persistence;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();

    T findById(int id);

    void add(T obj);

    void update(T obj);

    void delete(T obj);

}
