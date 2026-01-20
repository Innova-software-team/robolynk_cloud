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
    private long user_id;
    private long cart_id;
    private double total_price;
    private String payment_status;

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

    public long getCart_id() {
        return cart_id;
    }

    public void setCart_id(long cart_id) {
        this.cart_id = cart_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order(String payment_status, long user_id, double total_price, long cart_id) {
        this.payment_status = payment_status;
        this.user_id = user_id;
        this.total_price = total_price;
        this.cart_id = cart_id;
    }
}
