package org.ac.bibliotheque.cart.repository;

import org.ac.bibliotheque.cart.entity.Cart;
import org.ac.bibliotheque.user.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
