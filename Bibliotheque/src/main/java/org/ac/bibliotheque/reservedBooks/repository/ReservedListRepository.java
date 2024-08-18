package org.ac.bibliotheque.reservedBooks.repository;

import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.reservedBooks.entity.ReservedList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservedListRepository extends JpaRepository<ReservedList,Long> {
    Optional<ReservedList> findByUserDataId(Long userId);
    Optional<ReservedList> findByUserData(UserData userData);
}
