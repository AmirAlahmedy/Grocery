package com.amir.inventory.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

import java.util.UUID;

@Data
@Document(collection = "products")
@NoArgsConstructor
public class Product {

    @Id
    private String id;
    private String name;
    private Long price;
    private Integer stock;

    public  Product(String name, Long price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

}