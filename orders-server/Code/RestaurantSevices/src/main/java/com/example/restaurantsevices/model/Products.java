package com.example.restaurantsevices.model;
import jakarta.persistence.*;

@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long restaurant_id;
    private String product_name;
    private Long product_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
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
    public Products() {

    }
    public Products(Long id, Long restaurant_id, String product_name, Long product_price) {
        this.id = id;
        this.restaurant_id = restaurant_id;
        this.product_name = product_name;
        this.product_price = product_price;
    }

}
