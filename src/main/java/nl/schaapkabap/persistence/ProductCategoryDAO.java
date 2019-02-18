package nl.schaapkabap.persistence;

import nl.schaapkabap.models.ProductCategory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProductCategoryDAO extends DAO<ProductCategory> {


    public ProductCategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<ProductCategory> getAll() {
        return list((Query<ProductCategory>) namedQuery("Category.findAll"));
    }

    @Override
    public ProductCategory findById(int id) {
        return get(id);
    }

    @Override
    public void add(ProductCategory obj) {
        this.currentSession().save(obj);
    }

    @Override
    public void update(ProductCategory obj) {
        this.currentSession().merge(obj);
    }

    @Override
    public void delete(ProductCategory obj) {
        this.currentSession().delete(obj);
    }
}
