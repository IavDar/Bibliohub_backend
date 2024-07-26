package org.ac.bibliotheque.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ac.bibliotheque.role.Role;

import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_role",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;




}
