package com.grocery.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("1", "Apple", "Fresh Apple", 1.0);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> result = productController.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById("1")).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById("1");

        assertEquals(ResponseEntity.ok(product), response);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById("1")).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductById("1");

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        Product result = productController.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(eq("1"), any(Product.class))).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.updateProduct("1", product);

        assertEquals(ResponseEntity.ok(product), response);
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productService.updateProduct(eq("1"), any(Product.class))).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.updateProduct("1", product);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void testDeleteProduct() {
        when(productService.deleteProduct("1")).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct("1");

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productService.deleteProduct("1")).thenReturn(false);

        ResponseEntity<Void> response = productController.deleteProduct("1");

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}