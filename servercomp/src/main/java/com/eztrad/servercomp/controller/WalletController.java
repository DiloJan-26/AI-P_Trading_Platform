package com.eztrad.servercomp.controller;

// Step 71 - controller for wallet service

import com.eztrad.servercomp.model.*;
import com.eztrad.servercomp.service.OrderService;
import com.eztrad.servercomp.service.PaymentService;
import com.eztrad.servercomp.service.UserService;
import com.eztrad.servercomp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    // belongs to step 89
    @Autowired
    private OrderService orderService;

    // belongs to step 115
    @Autowired
    PaymentService paymentService;

    @GetMapping("api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }


    // step 72 - go and create a model for WalletTransaction and come back

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
            ) throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet recieverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser,recieverWallet, req.getAmount());

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    // step 73 - go and create the 0rder_api {nothing that it is OrderService}  and come back

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        // Step 89 - I already comment it at step 73 and came back now and do the changes because that time order service wasn't created
        Order order = orderService.gerOrderById(orderId);
        // above only related to step 89

        Wallet wallet = walletService.payOrderPayment(order,user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

        // Now let's move to create withdrawal api
        // Step 90 - create a model as Withdrawal
    }

    // Step 115 - continue to finish-up wallet controller from Payment controller code period

    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(name="order_id") Long orderId,
            @RequestParam(name="payment_id") String paymentId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status = paymentService.proceedPaymentOrder(order,paymentId);

        if(status){
            wallet = walletService.addBalance(wallet,order.getAmount());
        }

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

    }

    // so you finished up all the api by the end of 115th step now on - at 7:48:00
    // Step 116 - Transaction api part is missing so it will be added by me soon with own understanding - experimental and practical experiencing
    // at 09:03:40 - there is a show case about transaction controller get it
    // Before it go and try it out start to end process to understand the concept
    // go to properties
    // since all api are ready then go and check with postman
}
