package com.amir.bdd.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefinitions {

    @Autowired
    private WebTestClient webTestClient;

    // State shared across steps within the same scenario
    private WebTestClient.ResponseSpec responseSpec;
    private String generatedProductId;
    private Product createdProduct;

    // --- Background Step ---
    @Given("the product catalog is initialized")
    public void the_product_catalog_is_initialized() {
        // Option A: Call an endpoint to purge/reset your DB
        // Option B: Autowire your reactive Repository here and call .deleteAll().block()
    }

    // --- Scenario 1: Create a Product ---
    @When("the manager creates a product with name {string}, price {double}, and stock {int}")
    public void the_manager_creates_a_product_with_name_price_and_stock(String name, Double price, Integer stock) {
        Product payload = new Product(null, name, price, stock);

        responseSpec = webTestClient.post()
                .uri("/api/products")
                .bodyValue(payload)
                .exchange();

        // Capture the object if the request was accepted to check IDs later
        if (responseSpec.returnResult(Product.class).getStatus().is2xxSuccessful()) {
            createdProduct = responseSpec.returnResult(Product.class).getResponseBody().blockFirst();
            if (createdProduct != null) {
                generatedProductId = createdProduct.id();
            }
        }
    }

    @Then("the product creation response should return status code {int}")
    public void the_product_creation_response_should_return_status_code(Integer statusCode) {
        responseSpec.expectStatus().isEqualTo(statusCode);
    }

    @And("the created product should have a valid unique ID")
    public void the_created_product_should_have_a_valid_unique_id() {
        assertThat(generatedProductId).isNotNull().isNotBlank();
    }

    @And("the created product should have name {string} and price {double}")
    public void the_created_product_should_have_name_and_price(String expectedName, Double expectedPrice) {
        assertThat(createdProduct.name()).isEqualTo(expectedName);
        assertThat(createdProduct.price()).isEqualTo(expectedPrice);
    }

    // --- Scenario 2: Get a Product ---
    @Given("a product exists with name {string}, price {double}, and stock {int}")
    public void a_product_exists_with_name_price_and_stock(String name, Double price, Integer stock) {
        // Pre-populate the database by hitting your POST controller endpoint
        Product payload = new Product(null, name, price, stock);
        Product saved = webTestClient.post()
                .uri("/api/products")
                .bodyValue(payload)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Product.class)
                .getResponseBody()
                .blockFirst();

        assertThat(saved).isNotNull();
        generatedProductId = saved.id(); // Store the generated ID for the next 'When' step
    }

    @When("the manager requests the product using its unique ID")
    public void the_manager_requests_the_product_using_its_unique_id() {
        responseSpec = webTestClient.get()
                .uri("/api/products/{id}", generatedProductId)
                .exchange();
    }

    @Then("the product retrieval response should return status code {int}")
    public void the_product_retrieval_response_should_return_status_code(Integer statusCode) {
        responseSpec.expectStatus().isEqualTo(statusCode);
    }

    @And("the returned product should have name {string} and price {double}")
    public void the_returned_product_should_have_name_and_price(String expectedName, Double expectedPrice) {
        Product returned = responseSpec.returnResult(Product.class).getResponseBody().blockFirst();
        assertThat(returned).isNotNull();
        assertThat(returned.name()).isEqualTo(expectedName);
        assertThat(returned.price()).isEqualTo(expectedPrice);
    }

    // --- Scenario 3: List Products (Data Table Example) ---
    @Given("the following products exist in the catalog:")
    public void the_following_products_exist_in_the_catalog(DataTable dataTable) {
        // Convert Cucumber DataTable to a List of Maps
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            Product payload = new Product(
                    null,
                    row.get("name"),
                    Double.parseDouble(row.get("price")),
                    Integer.parseInt(row.get("stock"))
            );

            // Populate each row into your WebFlux app via POST
            webTestClient.post()
                    .uri("/api/products")
                    .bodyValue(payload)
                    .exchange()
                    .expectStatus().isCreated();
        }
    }

    @When("the manager requests a list of all products")
    public void the_manager_requests_a_list_of_all_products() {
        responseSpec = webTestClient.get()
                .uri("/api/products")
                .exchange();
    }

    @Then("the list response should return status code {int}")
    public void the_list_response_should_return_status_code(Integer statusCode) {
        responseSpec.expectStatus().isEqualTo(statusCode);
    }

    @And("the list should contain exactly {int} products")
    public void the_list_should_contain_exactly_products(Integer expectedSize) {
        List<Product> products = responseSpec.returnResult(Product.class).getResponseBody().collectList().block();
        assertThat(products).hasSize(expectedSize);
    }

    @And("the list should contain a product named {string}")
    public void the_list_should_contain_a_product_named(String expectedName) {
        List<Product> products = responseSpec.returnResult(Product.class).getResponseBody().collectList().block();
        boolean matches = products.stream().anyMatch(p -> p.name().equals(expectedName));
        assertThat(matches).isTrue();
    }
}
