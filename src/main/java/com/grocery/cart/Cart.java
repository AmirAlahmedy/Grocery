package com.grocery.cart;

import com.grocery.item.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Cart {
    @Id
    String Id;
    @OneToMany(mappedBy="cart")
    private List<Item> items;
}
