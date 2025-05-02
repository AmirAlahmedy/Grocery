package com.grocery.cart;

import com.grocery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Basic CRUD operations are inherited from JpaRepository

    // Add custom query methods if needed, e.g.,
    Cart findByUser(User user); //Find cart by User
}
