package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String email;

    private String langue;
    private String role;
    private LocalDateTime created_at;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<>();

    protected User() {}

    public User(String login, String firstname, String lastname, String role) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.created_at = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return login + "(" + firstname + " " + lastname + " - " + role + ")";
    }


}
