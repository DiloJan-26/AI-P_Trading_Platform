package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.domain.PaymentMethod;
import com.eztrad.servercomp.model.PaymentOrder;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.response.PaymentResponse;
import com.eztrad.servercomp.service.PaymentService;
import com.eztrad.servercomp.service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Step 114 - Lets create PaymentController
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authentication") String jwt
            ) throws Exception, RazorpayException, StripeException {

        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user,amount,paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLink(user,amount);
        } else {
            paymentResponse = paymentService.createStripePaymentLink(user,amount,order.getId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);

    }

    // Step 115 - here we took and go the code to Wallet Controller to finish-up the balance



}
