package com.example.restaurantsevices.model;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

@Entity
public class Product {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Restaurant restaurant;
    private String product_name;
    private Long product_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Long getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Long product_price) {
        this.product_price = product_price;
    }

    public Product() {

    }

    public Product(Restaurant restaurant, String product_name, Long product_price) {
        this.restaurant = restaurant;
        this.product_name = product_name;
        this.product_price = product_price;
    }

}
