package nl.schaapkabap.resource;

import javax.ws.rs.core.Response;

public interface CRUD<T> {
    /**
     * Creates in the DB an new object
     * @param obj
     * @return
     */
    Response create(T obj);

    /**
     * Reads from the DB an Object
     * @param id
     * @return
     */
    T read(int id);


    Response update(T obj);

    Response delete(int id);

}


