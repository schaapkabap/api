package nl.schaapkabap.models;

import com.fasterxml.jackson.annotation.JsonView;
import nl.schaapkabap.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity(name = "role")
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;

    public String getName() {
        return name;
    }
}
