package nl.schaapkabap.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.schaapkabap.views.View;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.*;

@Entity(name = "User")
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "User.findByCredentials",
                query = "SELECT p FROM User p WHERE p.username =:username"
        ),
        @NamedQuery(name = "User.findAll", query = "SELECT p FROM User p")
})
public class User implements Principal {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    private int id;

    @NotEmpty
    @Column(name = "username", nullable = false)
    @JsonView(View.Public.class)
    private String username;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    @JsonView(View.Public.class)
    private String password;

    @NotEmpty
    @Column(name = "email", nullable = false)
    @JsonView(View.Public.class)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(cascade = {
            CascadeType.PERSIST
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = { @JoinColumn(name = "fk_user", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "fk_role", nullable = false, updatable = false) }
    )
    @JsonView(View.Public.class)
    private Set<Role> roles = new HashSet<>();

    public int getId() {
        return id;
    }

    @JsonView(View.Internal.class)
    @Override
    public String getName() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView(View.Public.class)
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String role) {

        for (Role set : this.roles) {
            if (set.getName().toLowerCase().equals(role.toLowerCase()))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.id == ((User) obj).id;
        }

        return super.equals(obj);
    }
}
