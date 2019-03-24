package nl.schaapkabap.resource;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import nl.schaapkabap.models.Product;
import nl.schaapkabap.persistence.ProductDAO;
import nl.schaapkabap.service.ProductService;
import nl.schaapkabap.views.View;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;


@Singleton
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource  implements CRUD<Product> {

    private final ProductDAO dao;

    @Inject
    public ProductResource(ProductDAO dao) {
        this.dao = dao;
    }

    @GET
    @UnitOfWork
    @Override
    public Collection<Product> retrieveAll() {
        return dao.getAll();
    }

    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    @UnitOfWork
    @Override
    public Product read(@PathParam("id") int id) {

        Product product = dao.findById(id);
        return product;
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/{id}")
    @UnitOfWork
    @Override
    public Response delete(@PathParam("id") int id) {
        Product product = dao.findById(id);
        dao.delete(product);
        return Response.ok().build();
    }

    @POST
    @RolesAllowed("user")
    @Consumes({MediaType.APPLICATION_JSON})
    @UnitOfWork
    @Override
    public Response create(Product product) {
        dao.add(product);
        return Response.ok().build();
    }

    @PUT
    @RolesAllowed("user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    @UnitOfWork
    @Transactional
    @Override
    public Response update(Product product) {
        dao.update(product);
        return Response.ok().build();

    }

}
