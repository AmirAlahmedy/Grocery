package com.amir.inventory.reactive.service;

import com.amir.inventory.domain.Product;
import reactor.core.publisher.Flux;


public class ProductService {

    public Flux<Product> getProducts() {
        return Flux.empty();
    }
}
