package mysite2.MySpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "login")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min=2, message = "Не меньше 2 знаков")
    @Column(name = "username")
    private String username;

    @Size(min=2, message = "Не меньше 2 знаков")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public @Size(min = 2, message = "Не меньше 2 знаков") String getUsername() {
        return username;
    }

    public void setUsername(@Size(min = 2, message = "Не меньше 2 знаков") String username) {
        this.username = username;
    }


    public @Size(min = 2, message = "Не меньше 2 знаков") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 2, message = "Не меньше 2 знаков") String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
