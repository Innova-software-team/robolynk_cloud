package com.example.restaurantsevices.Repo;

import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.CartProduct;
import com.example.restaurantsevices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CartProductRepo extends JpaRepository<CartProduct, Long> {
    @Query("""
        SELECT cp.product
        FROM CartProduct cp
        WHERE cp.cart.id = :cartId
    """)
    Set<Product> findProductsByCartId(@Param("cartId") long cartId);

    @Query("""
        SELECT COALESCE(SUM(cp.product.productPrice * cp.quantity), 0)
        FROM CartProduct cp
        WHERE cp.cart = :cart
    """)
    long calculateCartValue(Cart cart);
}
