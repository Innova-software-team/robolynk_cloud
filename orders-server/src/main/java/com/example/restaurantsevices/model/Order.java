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
    private double totalPrice;
    private String paymentStatus;
    // needs longer length to accommodate stripe checkout url
    @Column(length = 1000)
    private String checkoutUrl;

    public Order() {

    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
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

    public Order(String paymentStatus, User user, double totalPrice, Cart cart, String checkoutUrl) {
        this.paymentStatus = paymentStatus;
        this.user = user;
        this.totalPrice = totalPrice;
        this.cart = cart;
        this.checkoutUrl = checkoutUrl;
    }
}
