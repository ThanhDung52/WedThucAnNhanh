package com.zosh.online_food_ordering.Service;

import com.stripe.exception.StripeException;
import com.zosh.online_food_ordering.Response.PaymentResponse;
import com.zosh.online_food_ordering.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
