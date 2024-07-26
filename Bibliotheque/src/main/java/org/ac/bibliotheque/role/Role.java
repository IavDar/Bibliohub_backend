package org.ac.bibliotheque.role;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Data
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;

}
