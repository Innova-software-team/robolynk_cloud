package com.example.restaurantsevices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="orders") // We cannot call this table order since order is a reserved keyword in postgres.
public class Order {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User user;
    @OneToOne
    @JoinColumn
    private Cart cart;
    private double total_price;
    private String payment_status;
    // needs longer length to accommodate stripe checkout url
    @Column(length = 1000)
    private String checkout_url;

    public Order() {

    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getCheckout_url() {
        return checkout_url;
    }

    public void setCheckout_url(String checkout_url) {
        this.checkout_url = checkout_url;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order(String payment_status, User user, double total_price, Cart cart, String checkout_url) {
        this.payment_status = payment_status;
        this.user = user;
        this.total_price = total_price;
        this.cart = cart;
        this.checkout_url = checkout_url;
    }
}
