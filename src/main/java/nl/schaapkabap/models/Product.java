package nl.schaapkabap.models;

import com.fasterxml.jackson.annotation.JsonView;
import nl.schaapkabap.views.View;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity(name = "Product")
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "Product.findByCredentials",
                query = "SELECT p FROM Product p WHERE p.name =:name"
        ),
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
})
public class Product {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    private int id;

    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;

    @Column(name = "description", nullable = false)
    @JsonView(View.Public.class)
    private String description;

    @JoinColumn(name="fk_product_category")
    @ManyToOne(targetEntity = ProductCategory.class,
            cascade = CascadeType.PERSIST)
    @JsonView(View.Public.class)
    private ProductCategory productCategory;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}