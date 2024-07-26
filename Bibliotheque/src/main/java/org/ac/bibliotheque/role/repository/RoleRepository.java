package org.ac.bibliotheque.role.repository;

import org.ac.bibliotheque.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByTitle(String title);


}
