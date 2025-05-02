package com.grocery.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public String getStatus(String orderId) {
        return "delivering";
    }
}
