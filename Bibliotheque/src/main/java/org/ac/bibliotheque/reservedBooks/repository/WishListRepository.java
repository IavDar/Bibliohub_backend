package org.ac.bibliotheque.reservedBooks.repository;

import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.reservedBooks.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<Wishlist,Long> {
    Optional<Wishlist> findByUserDataId(Long userId);
    Optional<Wishlist> findByUserData(UserData userData);
}
