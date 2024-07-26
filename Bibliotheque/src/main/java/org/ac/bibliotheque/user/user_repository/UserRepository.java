package org.ac.bibliotheque.user.user_repository;

import org.ac.bibliotheque.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);


}
