package com.example.restaurantsevices.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Cart {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartProduct> cartProducts = new HashSet<>();

    public Cart() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cart addProduct(Product product) {
        cartProducts.stream()
                .filter((cp) -> cp.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                cp -> cp.setQuantity(cp.getQuantity() + 1),
                () -> cartProducts.add(new CartProduct(this, product))
        );

        return this;
    }

    public Cart removeProductById(long productId) {
        cartProducts.removeIf(cartProduct -> cartProduct.getProduct().getId() == productId);
        return this;
    }

    public Set<CartProduct> getCartProducts() {
        return Collections.unmodifiableSet(cartProducts);
    }



    public Cart(Set<CartProduct> cartProducts, long id) {
        this.cartProducts = cartProducts;
        this.id = id;
    }
}
