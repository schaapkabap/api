package nl.schaapkabap.models;

import com.fasterxml.jackson.annotation.JsonView;
import nl.schaapkabap.views.View;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity(name = "Product")
@Table(name = "produdcts")
@NamedQueries({
        @NamedQuery(
                name = "Product.findByCredentials",
                query = "SELECT p FROM Product p WHERE p.username =:username"
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

    @JoinColumn(name="fk_category")
    @ManyToOne(targetEntity = Category.class,
            cascade = CascadeType.PERSIST)
    @JsonView(View.Public.class)
    private Category category;


}