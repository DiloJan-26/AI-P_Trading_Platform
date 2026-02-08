package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.PaymentMethod;
import com.eztrad.servercomp.model.PaymentOrder;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

// Step 109 - create PaymentService
public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    // Step 110 - go and create a PaymentResponse and come back

    PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

    // now go for the implementation
}
