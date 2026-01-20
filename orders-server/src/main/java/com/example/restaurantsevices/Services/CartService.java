package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.CartProductRepo;
import com.example.restaurantsevices.Repo.CartRepo;
import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartProductRepo cartProductRepo;

    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepo.save(cart);
    }

    public Optional<Set<Product>> findProductsByCartId(long cartId) {
        return cartRepo.findById(cartId)
                .map(cart -> cartProductRepo.findProductsByCart_Id(cartId));
    }

    public Optional<Cart> addProductToCart(long cartId, Product product) {
        return cartRepo.findById(cartId)
                .map((Cart cart) -> cart.addProduct(product))
                .map(cartRepo::save);
    }

    public Optional<Cart> removeProductFromCart(long cartId, long productId) {
        return cartRepo.findById(cartId)
                .map((Cart cart) -> cart.removeProductById(productId))
                .map(cartRepo::save);
    }

    public Optional<Long> calculateCartValue(long cartId) {
        return cartRepo.findById(cartId)
                .map(cartProductRepo::calculateCartValue);
    }
}
