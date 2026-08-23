package com.amir.inventory.domain;

import java.util.List;

import com.amir.inventory.constants.OrderStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document(collection = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @Id
    private String id;
    private String userId;
    private List<LineItem> lineItems;
    private Long total;
    private OrderStatus orderStatus;
    private String responseMessage;

    public Order setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Order setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

}
