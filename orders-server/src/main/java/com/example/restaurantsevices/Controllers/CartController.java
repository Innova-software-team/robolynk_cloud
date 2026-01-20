package com.example.restaurantsevices.Controllers;

import com.example.restaurantsevices.Services.CartService;
import com.example.restaurantsevices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllCarts(){
        return ResponseEntity.ok(cartService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

    @GetMapping("/{cartId}/value")
    public ResponseEntity<?> calculateCartValue(@PathVariable Long cartId) {
        return cartService.calculateCartValue(cartId)
                .map((value) -> ResponseEntity.ok().body(value))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{cartId}/products")
    public ResponseEntity<?> addProductToCart(@PathVariable long cartId, @RequestBody Product product) {
        return cartService.addProductToCart(cartId, product)
                .map(saved -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{cartId}/products")
    public ResponseEntity<Set<Product>> getProductsFromCart(@PathVariable long cartId) {
        return cartService.findProductsByCartId(cartId)
                .map((products) -> ResponseEntity.ok().body(products))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        return cartService.removeProductFromCart(cartId, productId)
                .map((cart) -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }
}