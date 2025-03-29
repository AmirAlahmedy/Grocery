package com.grocery.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Basic CRUD operations are inherited from JpaRepository

    // Add custom query methods if needed, e.g.,
    List<Product> findByNameContainingIgnoreCase(String name); // Search by name

//    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByIsAvailableTrue(); // find only available products

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% AND p.isAvailable = true")
    List<Product> searchProducts(@Param("keyword") String keyword);
}
