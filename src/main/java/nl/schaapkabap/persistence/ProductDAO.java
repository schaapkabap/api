package nl.schaapkabap.persistence;

import nl.schaapkabap.models.Product;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO extends DAO<Product> {

    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Product> getAll() {
        return list((Query<Product>) namedQuery("Product.findAll"));
    }


    @Override
    public Product findById(int id) {
        return get(id);
    }

    @Override
    public void add(Product obj) {
        this.currentSession().save(obj);
    }

    @Override
    public void update(Product obj) {
        this.currentSession().merge(obj);

    }

    @Override
    public void delete(Product obj) {
        this.currentSession().delete(obj);
    }
}
