package com.amir.inventory.reactive.controller;

import com.amir.inventory.domain.Product;
import com.amir.inventory.reactive.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Flux<Product> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping("/products")
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

}
