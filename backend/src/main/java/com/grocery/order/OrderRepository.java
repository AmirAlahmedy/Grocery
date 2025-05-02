package com.grocery.order;

import com.grocery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Basic CRUD operations are inherited from JpaRepository

    // Add custom query methods if needed, e.g.,
    List<Order> findByUser(User user);

    List<Order> findByOrderStatus(String orderStatus);
//    List<Order> findByUserIdOrderByOrderDateDesc(Long userId); //Orders by User, most recent first
}
