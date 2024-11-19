package com.codej.uptask.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String tokenPassword;

    private Boolean enabled=true;

    @Column(name = "account_no_expired")
    private Boolean accountNoExpired=true;

    @Column(name = "account_no_locked")
    private Boolean accountNoLocked=true;

    @Column(name = "credentials_no_expired")
    private Boolean credentialsNoExpired=true;

    @JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> roles;

    public void  addRol(Rol rol){
        if (roles == null){
            roles = new LinkedList<Rol>();
        }
        roles.add(rol);
    }

}
