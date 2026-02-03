package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.CartProductRepo;
import com.example.restaurantsevices.Repo.CartRepo;
import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.CartProduct;
import com.example.restaurantsevices.model.Product;
import com.example.restaurantsevices.model.User;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    private static CartService instance;

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartProductRepo cartProductRepo;
    @Autowired
    private UserService userService;

    @PostConstruct
    private void initInstance() {
        instance = this;
    }

    public static CartService getInstance() {
        return instance;
    }

    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepo.save(cart);
    }

    public Optional<Set<Product>> findProductsByCartId(long cartId) {
        return cartRepo.findById(cartId)
                .map(cart -> cartProductRepo.findProductsByCartId(cartId));
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

    public Cart getOrCreateCartByUserId(String userId) {
        User user = userService.getUserById(userId);
        if (user.getCurrentCart() == null) {
            user.setCurrentCart(new Cart());
            user = userService.save(user);
        }
        return user.getCurrentCart();
    }
    public Cart getByUserId(String userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getCurrentCart();
    }

    public Cart cloneCart(Cart sourceCart) {
        Cart clonedCart = new Cart();
        for (CartProduct product : sourceCart.getCartProducts()) {
            clonedCart.addProduct(product.getProduct(), product.getQuantity());
        }

        return cartRepo.save(clonedCart);
    }
}
