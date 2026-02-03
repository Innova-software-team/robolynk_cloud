package com.example.restaurantsevices.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurantsevices.Services.OrderService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @RequestMapping(value ="/", method = RequestMethod.POST)
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        try {
            orderService.placeOrder(userId);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        // Redirect to checkout route; order includes checkout URL
        return ResponseEntity.status(302).header("Location", "/checkout").body("Order placed successfully. Redirecting to checkout...");
    }
}
