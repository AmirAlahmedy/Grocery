package com.grocery.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Basic CRUD operations are inherited from JpaRepository

    // Add custom query methods if needed, e.g.,
    List<OrderItem> findByOrder(Order order); //Get items for a specific Order
}
