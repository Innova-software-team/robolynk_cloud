package com.example.restaurantsevices.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurantsevices.Services.UserService;
import com.example.restaurantsevices.Services.payment.StripePaymentService;
import com.example.restaurantsevices.model.Order;
import com.example.restaurantsevices.model.User;
import com.stripe.exception.SignatureVerificationException;

@RestController
public class PaymentController {
    @Autowired
    private StripePaymentService paymentService;
    @Autowired
    private UserService userService;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ResponseEntity<String> getCheckoutPage(@AuthenticationPrincipal Jwt jwt) {
        // fetch user
        String userId = jwt.getSubject();
        User user = userService.getUserById(userId);
        Order order = user.getCurrentOrder();

        if (order == null) {
            return ResponseEntity.status(400).body("No current order found for user.");
        }

        return ResponseEntity.status(302).header("Location", order.getCheckout_url()).build();
    }

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            paymentService.handleWebhookEvent(payload, sigHeader);
            
        } catch (SignatureVerificationException e) {
            System.out.println("Webhook error while validating signature.\n" + e.getMessage());
            return ResponseEntity.status(400).body("Webhook error while validating signature.");

        } catch (IllegalStateException e) {
            System.out.println("Failed to deserialize webhook event. Likely due to an API version mismatch.");
            return ResponseEntity.status(400).body(e.getMessage());
        }

        return ResponseEntity.ok("");
    }
}
