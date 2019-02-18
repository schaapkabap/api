package nl.schaapkabap.resource;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import nl.schaapkabap.models.ProductCategory;
import nl.schaapkabap.persistence.ProductCategoryDAO;
import nl.schaapkabap.views.View;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Singleton
@Path("/categorys")
@Produces(MediaType.APPLICATION_JSON)
public class ProductCategoryResource implements CRUD<ProductCategory> {


    private ProductCategoryDAO dao;

    public ProductCategoryResource(ProductCategoryDAO dao) {
        this.dao =  dao;
    }

    @POST
    @RolesAllowed("user")
    @Consumes({MediaType.APPLICATION_JSON})
    @UnitOfWork
    @Override
    public Response create(ProductCategory obj) {
        dao.add(obj);
        return Response.ok().build();
    }

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    @JsonView(View.Public.class)
    @UnitOfWork
    @Override
    public ProductCategory read(@PathParam("id") int id) {
        ProductCategory productCategory = this.dao.findById(id);

        return productCategory;
    }
    @GET
    @JsonView(View.Public.class)
    @RolesAllowed("user")
    @UnitOfWork
    @Override
    public Collection<ProductCategory> retrieveAll() {
        return this.dao.getAll();
    }

    @PUT
    @RolesAllowed("user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    @UnitOfWork
    @Transactional
    @Override
    public Response update(ProductCategory obj) {
        this.dao.update(obj);
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/{id}")
    @UnitOfWork
    @Override
    public Response delete(@PathParam("id") int id) {

        ProductCategory productCategory = this.dao.findById(id);
        this.dao.delete(productCategory);
        return Response.ok().build();
    }
}
