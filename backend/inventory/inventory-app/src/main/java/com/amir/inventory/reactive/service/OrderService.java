package com.amir.inventory.reactive.service;

import com.amir.inventory.constants.OrderStatus;
import com.amir.inventory.domain.Order;
import com.amir.inventory.reactive.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Mono<Order> handleOrder(Order order) {
        return Flux.fromIterable(order.getLineItems())
                .flatMap(l -> productRepository.findById(l.getProductId()))
                .flatMap(p -> {
                    int q = order.getLineItems().stream()
                            .filter(l -> l.getProductId().equals(p.getId()))
                            .findAny().get()
                            .getQuantity();
                    if (p.getStock() >= q) {
                        p.setStock(p.getStock() - q);
                        return productRepository.save(p);
                    } else {
                        return Mono.error(new RuntimeException("Product is out of stock: " + p.getId()));
                    }
                })
                .then(Mono.just(order.setOrderStatus(OrderStatus.valueOf("SUCCESS"))));
    }

    @Transactional
    public Mono<Order> revertOrder(Order order) {
        return Flux.fromIterable(order.getLineItems())
                .flatMap(l -> productRepository.findById(l.getProductId()))
                .flatMap(p -> {
                    int q = order.getLineItems().stream()
                            .filter(l -> l.getProductId().equals(p.getId()))
                            .findAny().get()
                            .getQuantity();
                    p.setStock(p.getStock() + q);
                    return productRepository.save(p);
                })
                .then(Mono.just(order.setOrderStatus(OrderStatus.valueOf("SUCCESS"))));
    }
}
