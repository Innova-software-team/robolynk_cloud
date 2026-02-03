package com.example.restaurantsevices.Services.payment;

import com.example.restaurantsevices.model.Order;

public interface PaymentService {
    /// Initiate a payment for a given cart, and
    /// return the checkout redirect url.
    String initiatePaymentFlow(Order order) throws Exception;
}
