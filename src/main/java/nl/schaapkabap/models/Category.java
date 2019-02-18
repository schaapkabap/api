package nl.schaapkabap.models;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "Category")
@Table(name = "catergorys")
@NamedQueries({
        @NamedQuery(
                name = "Category.findById",
                query = "SELECT p FROM Category p WHERE p.id =:id"
        ),
        @NamedQuery(name = "Category.findAll", query = "SELECT p FROM Category p")
})
public class Category {


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
