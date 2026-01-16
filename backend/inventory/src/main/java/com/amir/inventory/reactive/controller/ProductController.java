package com.amir.inventory.reactive.controller;

import com.amir.inventory.domain.Product;
import com.amir.inventory.reactive.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getProducts();
    }

}
