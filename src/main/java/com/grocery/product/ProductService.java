package com.grocery.product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(String id) {
        return products.stream().filter(product -> product.getProductId().equals(id)).findFirst();
    }

    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    public Optional<Product> updateProduct(String id, Product productDetails) {
        return getProductById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            return product;
        });
    }

    public boolean deleteProduct(String id) {
        return products.removeIf(product -> product.getProductId().equals(id));
    }
}