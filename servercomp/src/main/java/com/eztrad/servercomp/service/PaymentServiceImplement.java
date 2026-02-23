package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.PaymentMethod;
import com.eztrad.servercomp.domain.PaymentOrderStatus;
import com.eztrad.servercomp.model.PaymentOrder;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.PaymentOrderRepository;
import com.eztrad.servercomp.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Step 111 - create PaymentService Implementation
// Step 112 - first go and create the Repo for it
@Service
public class PaymentServiceImplement implements PaymentService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    // Stripe API configuration
    // Add your Stripe secret key in application.properties file
    // stripe.api.key=your_stripe_secret_key
    @Value("${stripe.api.key}")
    private String stripeSecretKey;




    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);  // Set initial status to PENDING
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("payment order not found"));
    }


    // Step 113 - Payment verification using Stripe only
    // Updated: Removed Razorpay, using only Stripe payment gateway
    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws Exception {

        // Check if payment order status is null or not PENDING
        if(paymentOrder.getStatus() == null) {
            paymentOrder.setStatus(PaymentOrderStatus.PENDING);
        }

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            // For Stripe payment verification
            // The paymentId here is actually the Stripe Session ID
            try {
                Stripe.apiKey = stripeSecretKey;

                // Retrieve the Stripe Session to verify payment
                Session session = Session.retrieve(paymentId);

                // Check if payment was successful
                if(session.getPaymentStatus().equals("paid")) {
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                    return true;
                }

                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;

            } catch (StripeException e) {
                System.out.println("Error verifying Stripe payment: " + e.getMessage());
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;
            }
        }
        return false;
    }

    // Step 114 - Create Stripe payment link with session ID in success URL
    // Updated: Enhanced to include payment session ID in callback URL for wallet deposit
    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                // Include {CHECKOUT_SESSION_ID} in success URL - Stripe will replace it with actual session ID
                .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId + "&payment_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount*100)  // Stripe uses cents
                                                .setProductData(
                                                        SessionCreateParams
                                                                .LineItem
                                                                .PriceData
                                                                .ProductData
                                                                .builder()
                                                                .setName("Top up wallet")
                                                                .build()
                                                ).build()
                                ).build()
                ).build();

        Session session = Session.create(params);  // this Session class got from com.stripe.model.checkout.Session

        System.out.println("session _____ " + session);

        PaymentResponse res = new PaymentResponse();
        res.setPayment_url(session.getUrl());

        return res;
    }

    // Step 114 - Lets move on to create the controller for this - PaymentController
}
