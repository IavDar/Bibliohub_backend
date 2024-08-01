package org.ac.bibliotheque.user.user_repository;

import org.ac.bibliotheque.user.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData,Long> {

    UserData findByEmail(String email);


}
