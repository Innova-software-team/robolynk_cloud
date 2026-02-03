package com.example.restaurantsevices.Services.payment;

import java.util.ArrayList;
import java.util.List;

import com.example.restaurantsevices.Services.OrderService;
import com.example.restaurantsevices.model.Cart;
import com.example.restaurantsevices.model.Order;
import com.stripe.StripeClient;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {
    private static StripePaymentService instance;

    @Value("${stripe.api.key}")
    private String apiKey;
    @Value("${stripe.success.url}")
    private String successUrl;
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private StripeClient stripeClient;

    @PostConstruct
    private void initStripeClient() {
        instance = this;
        stripeClient = new StripeClient(apiKey);
    }

    public static StripePaymentService getInstance() {
        return instance;
    }

    @Override
    public String initiatePaymentFlow(Order order) throws StripeException, IllegalStateException {
        // Ensure cart is not empty
        if (order.getCart().getCartProducts().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Initiate Stripe payment flow using the cart details
        Cart cart = order.getCart();

        // create line items
        List<SessionCreateParams.LineItem> lineItems = createLineItemsFromCart(cart);

        // Create checkout session
        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .addAllLineItem(lineItems)
            .setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
                .setCaptureMethod(SessionCreateParams.PaymentIntentData.CaptureMethod.MANUAL)
                .putMetadata("order_id", Long.toString(order.getId()))
                .build()
            )
            .build();
            
        Session session;
        try {
            session = stripeClient.v1().checkout().sessions().create(params);
        } catch (StripeException e) {
            OrderService.getInstance().cancelOrder(order);
            
            /// Log error
            System.out.println("Stripe API error: " + e.getMessage());
            throw e;
        }
        
        // Return the checkout URL
        return session.getUrl();
    }

    private List<SessionCreateParams.LineItem> createLineItemsFromCart(Cart cart) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (var cartProduct : cart.getCartProducts()) {
            var product = cartProduct.getProduct();
            var quantity = cartProduct.getQuantity();

            var lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("gbp")
                        .setUnitAmount((long)(product.getProduct_price() * 100))
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(product.getProduct_name())
                                    .build()
                        )
                        .build()
                )
                .setQuantity((long)quantity)
                .build();

            lineItems.add(lineItem);
        }

        return lineItems;
    }

    public void handleWebhookEvent(String payload, String sigHeader) 
                throws SignatureVerificationException, IllegalStateException {
        Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

        Object stripeObject = dataObjectDeserializer.getObject().orElse(null);
        if (stripeObject == null)
            throw new IllegalStateException("Deserialization failed.");

        System.out.println("Received event: " + event.getType());
        switch (event.getType()) {
            case "payment_intent.amount_capturable_updated":
                handlePaymentAuthorisation((PaymentIntent) stripeObject);
                break;
        
            default:
                // warn unknown event type
                System.out.println("Unknown event type: " + event.getType());
                break;
        }
    }

    private void handlePaymentAuthorisation(PaymentIntent paymentIntent) {
        // Payment succeeded, so notify the order service to fulfill the order.
        Long orderId = Long.parseLong(paymentIntent.getMetadata().get("order_id"));
        System.out.println("Order ID: " + orderId);

        /// TODO: Authorise the amount captured
        
        OrderService.getInstance().handlePaymentAuthorisation(orderId);
    }
}
