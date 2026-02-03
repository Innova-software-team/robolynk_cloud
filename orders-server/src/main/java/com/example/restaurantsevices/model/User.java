package com.example.restaurantsevices.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="users") // We cannot call this table user since user is a reserved keyword in postgres.
public class User {
    @Id
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    private Cart currentCart;
    @OneToOne(cascade = CascadeType.ALL)
    private Order currentOrder;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User() { }

    public User(String id, Cart currentCart) {
        this.id = id;
        this.currentCart = currentCart;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Cart getCurrentCart() {
        return currentCart;
    }
    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }
    public Order getCurrentOrder() {
        return currentOrder;
    }
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        this.currentOrder = order;
    }
}
