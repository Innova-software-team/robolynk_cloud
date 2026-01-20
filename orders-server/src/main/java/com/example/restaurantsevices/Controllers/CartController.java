package com.example.restaurantsevices.Controllers;

import com.example.restaurantsevices.Repo.CartProductRepo;
import com.example.restaurantsevices.Repo.CartRepo;
import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartProductRepo cartProductRepo;

    @GetMapping
    public ResponseEntity<?> getAllCarts(){
        return ResponseEntity.ok(cartRepo.findAll());
    }

    @GetMapping("/{cartId}/value")
    public ResponseEntity<?> calculateCartValue(@PathVariable Long cartId) {
        return cartRepo.findById(cartId)
                .map(cartProductRepo::calculateCartValue)
                .map((value) -> ResponseEntity.ok().body(value))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{cartId}/products")
    public ResponseEntity<?> addProductToCart(@PathVariable long cartId, @RequestBody Product product) {
        return cartRepo.findById(cartId)
                .map((Cart cart) -> cart.addProduct(product))
                .map(cartRepo::save)
                .map(saved -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{cartId}/products")
    public ResponseEntity<Set<Product>> getProductsFromCart(@PathVariable long cartId) {
        return cartRepo.findById(cartId)
                .map((cart) -> ResponseEntity.ok(
                        cartProductRepo.findProductsByCart_Id(cartId))
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        return cartRepo.findById(cartId)
                .map((cart) -> cart.removeProductById(productId))
                .map(cartRepo::save)
                .map((cart) -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }
}