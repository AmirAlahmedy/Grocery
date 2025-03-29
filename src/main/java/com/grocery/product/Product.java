package com.grocery.product;

import com.grocery.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    String productId;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String unit;  // kg, piece, liter

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;
}
