package com.grocery.item;

import com.grocery.cart.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {
   @Id
    String Id;
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
}
