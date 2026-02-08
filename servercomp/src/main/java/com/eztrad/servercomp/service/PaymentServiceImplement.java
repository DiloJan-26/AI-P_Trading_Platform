package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.PaymentMethod;
import com.eztrad.servercomp.domain.PaymentOrderStatus;
import com.eztrad.servercomp.model.PaymentOrder;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.PaymentOrderRepository;
import com.eztrad.servercomp.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Step 111 - create PaymentService Implementation
// Step 112 - first go and create the Repo for it
@Service
public class PaymentServiceImplement implements PaymentService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    // In to the application.properties file we will give the api keys for stripe and razorpay
        // razorpay.api.key=YOUR_KEY_ID (we do the step later this suggestion got from chatgpt)
        // razorpay.api.secret=YOUR_SECRET (we do the step later this suggestion got from chatgpt)
        // also if any to add for stripe add it
    // so it can bring like this

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;



    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("payment order not found"));
    }


    // it's a most important step here
    // In here you proceed with Razorpay maven dependency and also for Stripe Maven dependency
    // Step 113 - go and search them in website and add it in pom.xml and come back
    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){

            // for Razorpay
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorPay = new RazorpayClient(apiKey, apiSecretKey);
                Payment payment = razorPay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepository.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException {
        Long Amount = amount*100;

        try{
            // initialize the razorpay client with your keyId and secret
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);

            // create a json object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject(); // noted will do at code optimize period
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            // create a json object with the custom details
            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            // Create the json object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            // set the remainder settings
            paymentLinkRequest.put("reminder_enable", true);

            // set the callback_url and method
            paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
            paymentLinkRequest.put("callback_method", "get");

            // create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentResponse res = new PaymentResponse();
            res.setPayment_url(paymentLinkUrl);

            return res;

        } catch (RazorpayException e) {

            System.out.println("Error creating payment link" + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }



    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount*100)
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
