package org.ac.bibliotheque.user.user_repository;

import org.ac.bibliotheque.role.Role;
import org.ac.bibliotheque.user.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData,Long> {

    Optional<UserData> findByEmail(String email);


}
