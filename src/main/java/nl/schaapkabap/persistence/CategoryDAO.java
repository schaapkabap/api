package nl.schaapkabap.persistence;

import nl.schaapkabap.models.Category;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAO extends DAO<Category> {


    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Category> getAll() {
        return list((Query<Category>) namedQuery("Category.findAll"));
    }

    @Override
    public Category findById(int id) {
        return get(id);
    }

    @Override
    public void add(Category obj) {
        this.currentSession().save(obj);
    }

    @Override
    public void update(Category obj) {
        this.currentSession().merge(obj);
    }

    @Override
    public void delete(Category obj) {
        this.currentSession().delete(obj);
    }
}
