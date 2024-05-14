package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=])(?=\\S+$).{6,}$",
            message = "Password must be at least 6 characters long, contain at least one uppercase letter, and contain at least one special character from !@#$%^&*()-+=")
    @NotNull(message = "Must not be empty")
    private String password;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=])(?=\\S+$).{6,}$",
            message = "Password must be at least 6 characters long, contain at least one uppercase letter, and contain at least one special character from !@#$%^&*()-+=")
    @Transient
    private String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z\\-\\s]{2,18}$",
            message = "first name must be of 2 to 18 length with no special characters and numbers")
    @NotNull(message = "Must not be empty")
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z\\-\\s]{2,18}$",
            message = "last name must be of 2 to 18 length with no special characters and numbers")
    @NotNull(message = "Must not be empty")
    private String lastname;

    private String email;
    private String langue;
    private String role;
    private LocalDateTime created_at;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Representation> representations = new ArrayList<>();

    protected User() {}

    public User(String login, String firstname, String lastname,String role) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.created_at = LocalDateTime.now();
    }
    public User addRole(Role role) {
        if(!this.roles.contains(role)) {
            this.roles.add(role);
            role.addUser(this);
        }

        return this;
    }

    public User removeRole(Role role) {
        if(this.roles.contains(role)) {
            this.roles.remove(role);
            role.getUsers().remove(this);
        }

        return this;
    }
    public User addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.addUser(this);
        }

        return this;
    }

    public User removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            representation.getUsers().remove(this);
        }

        return this;
    }


    @Override
    public String toString() {
        return login + "(" + firstname + " " + lastname + " - " + role + ")";
    }


}
