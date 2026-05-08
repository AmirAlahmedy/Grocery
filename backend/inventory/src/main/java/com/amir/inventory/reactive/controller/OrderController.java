package com.amir.inventory.reactive.controller;

import com.amir.inventory.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
public class OrderController {
    @PostMapping("/order")
    public Mono<Order> processOrder(@RequestBody Order order) {
        return Mono.just(order);
    }
}
