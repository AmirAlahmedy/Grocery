package com.amir.inventory.reactive.controller;

import com.amir.inventory.domain.Product;
import com.amir.inventory.reactive.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing consumer products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    @Operation(summary = "List all products", description = "List all products in the inventory.")
    @ApiResponse(responseCode = "200", description = "Products successfully retrieved")
    public Flux<Product> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a product", description = "Creates a product using the details sent in the body.")
    @ApiResponse(responseCode = "201", description = "Product successfully created")
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Fetches details of a product using its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Product successfully found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
