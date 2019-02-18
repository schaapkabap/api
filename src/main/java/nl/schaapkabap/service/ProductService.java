package nl.schaapkabap.service;

import nl.schaapkabap.models.Product;
import nl.schaapkabap.models.User;
import nl.schaapkabap.persistence.DAO;

public class ProductService extends Service<Product> {


    public ProductService(DAO<Product> dao) {
        super(dao);
    }

    @Override
    boolean hasAccess(User user, Product obj) {
        return false;
    }
}
