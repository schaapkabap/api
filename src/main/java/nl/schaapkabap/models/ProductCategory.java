package nl.schaapkabap.models;

import com.fasterxml.jackson.annotation.JsonView;
import nl.schaapkabap.views.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "Category")
@Table(name = "product_category")
@NamedQueries({
        @NamedQuery(
                name = "Category.findById",
                query = "SELECT p FROM Category p WHERE p.id =:id"
        ),
        @NamedQuery(name = "Category.findAll", query = "SELECT p FROM Category p")
})
public class ProductCategory {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    private int id;

    private String name;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
