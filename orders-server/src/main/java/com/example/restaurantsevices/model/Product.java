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
    private String productName;
    private Long productPrice;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Product() {

    }

    public Product(Restaurant restaurant, String productName, Long productPrice) {
        this.restaurant = restaurant;
        this.productName = productName;
        this.productPrice = productPrice;
    }

}
