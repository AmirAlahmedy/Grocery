Feature: Product Management
  As a store manager
  I want to manage products in the catalog
  So that customers can see what items are available for purchase

  Background:
    Given the product catalog is initialized

  Scenario: Successfully create a new product
    When the manager creates a product with name "Wireless Mouse", price 29.99, and stock 50
    Then the product creation response should return status code 201
    And the created product should have a valid unique ID
    And the created product should have name "Wireless Mouse" and price 29.99

  Scenario: Successfully retrieve a product by ID
    Given a product exists with name "Mechanical Keyboard", price 89.99, and stock 15
    When the manager requests the product using its unique ID
    Then the product retrieval response should return status code 200
    And the returned product should have name "Mechanical Keyboard" and price 89.99

  Scenario: Retrieve all products in the catalog
    Given the following products exist in the catalog:

      | name                | price  | stock |
      | Wireless Mouse      | 29.99  | 50    |
      | Mechanical Keyboard | 89.99  | 15    |
      | 4K Monitor          | 299.50 | 5     |
    When the manager requests a list of all products
    Then the list response should return status code 200
    And the list should contain exactly 3 products
    And the list should contain a product named "Mechanical Keyboard"
