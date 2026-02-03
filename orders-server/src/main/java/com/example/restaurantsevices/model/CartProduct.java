package com.example.restaurantsevices.model;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Table(uniqueConstraints = {
            @UniqueConstraint(columnNames = {"cart_id", "product_id"})
        })
public class CartProduct {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false)
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false)
    private Product product;

    public CartProduct() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartProduct(Cart cart, Product product) {
        this.quantity = 1;
        this.cart = cart;
        this.product = product;
    }

    public CartProduct(Cart cart, Product product, int quantity) {
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }
}
