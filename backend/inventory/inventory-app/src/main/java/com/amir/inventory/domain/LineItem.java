package com.amir.inventory.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "line_items")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {

    private String productId;
    private int quantity;

}