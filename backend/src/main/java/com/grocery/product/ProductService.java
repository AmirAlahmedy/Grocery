package com.grocery.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    @Autowired
    ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(repository.findByProductId(id));
    }

    public Product createProduct(Product product) {
        products.add(product);
        repository.save(product);
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