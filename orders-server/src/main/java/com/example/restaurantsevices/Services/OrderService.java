package com.example.restaurantsevices.Services;

import com.example.restaurantsevices.Repo.OrderRepo;
import com.example.restaurantsevices.Services.payment.StripePaymentService;
import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.Order;
import com.example.restaurantsevices.model.User;
import com.stripe.exception.StripeException;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private static OrderService instance;

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private StripePaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @PostConstruct
    private void initInstance() {
        instance = this;
    }

    public static OrderService getInstance() {
        return instance;
    }

    public Order placeOrder(String userId) throws IllegalStateException, StripeException {
        // fetch user and cart for user
        User user = userService.getOrCreateUserById(userId);
        Cart cart = user.getCurrentCart();

        // Ensure cart is not empty
        if (cart.getCartProducts().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Ensure cart is not currently associated with an order
        if (orderRepo.existsByCartId(cart.getId())) {
            throw new IllegalStateException("An order already exists for this cart");
        }

        // Create order object
        Order order = new Order();
        order.setCart(cart);
        order.setUser(user);
        
        order = orderRepo.save(order);

        user.addOrder(order);
        userService.save(user);

        // Require payment for the order and store checkout url
        String checkoutUrl = paymentService.initiatePaymentFlow(order);
        order.setCheckoutUrl(checkoutUrl);
        return orderRepo.save(order);
    }
    public List<Order> getAllOrders() { return orderRepo.findAll(); }
    public Order getOrderById(Long id) { return orderRepo.findById(id).orElse(null); }
    public void deleteOrderById(Long id) { orderRepo.deleteById(id); }

    public Order getLatestOrderByUserId(String userId) { return orderRepo.findTopByUserIdOrderByIdDesc(userId); }

    public Order getByCart(Cart curruntCart) {
        return orderRepo.findByCartId(curruntCart.getId());
    }
    public void cancelOrder(Order order) {
        /// Current cart is associated with a failed order.
        /// Clone the cart and set the cloned cart as current cart for user
        /// Also reset the currentOrder to null.
        User user = order.getUser();
        Cart clonedCart = cartService.cloneCart(order.getCart());
        
        user.setCurrentCart(clonedCart);
        user.setCurrentOrder(null);

        userService.save(user);
    }

    public void handlePaymentAuthorisation(Long orderId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new IllegalStateException("Authorised order id does not exist: " + orderId);
        }
        
        order.setPaymentStatus("AUTHORIZED");
        orderRepo.save(order);

        /// TODO: Create delivery request
    }
}
