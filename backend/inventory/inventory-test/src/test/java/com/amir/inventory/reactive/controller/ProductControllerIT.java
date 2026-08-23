package com.amir.inventory.reactive.controller;

import com.amir.inventory.InventoryApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
    classes = InventoryApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
@ActiveProfiles("test")
public class ProductControllerIT {

  @Container @ServiceConnection
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

  @Autowired private WebTestClient webTestClient;

  @Autowired private ReactiveMongoTemplate mongoTemplate;

  @DynamicPropertySource
  static void setMongoProperties(DynamicPropertyRegistry registry) {
    // Overrides application.properties with the container's random port
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @AfterEach
  void cleanUp() {
    // Clear collections between tests to keep them isolated
    mongoTemplate.dropCollection("products").block();
  }

  @Test
  void shouldCreateAndReturnNewProduct() {
    Product requestBody = new Product(null, "Gaming Laptop", 1200.00, 10);

    // 1. HTTP POST - Create Product
    Product savedProduct =
        webTestClient
            .post()
            .uri("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Product.class)
            .value(
                product -> {
                  assert product.id() != null;
                  assert product.name().equals("Gaming Laptop");
                })
            .returnResult()
            .getResponseBody();

    // 2. HTTP GET - Retrieve the created product by ID
    webTestClient
        .get()
        .uri("/api/products/{id}", savedProduct.id())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(savedProduct.id())
        .jsonPath("$.name")
        .isEqualTo("Gaming Laptop")
        .jsonPath("$.price")
        .isEqualTo(1200.00);
  }
}
