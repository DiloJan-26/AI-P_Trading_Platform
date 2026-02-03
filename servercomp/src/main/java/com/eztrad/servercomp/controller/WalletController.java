package com.eztrad.servercomp.controller;

// Step 71 - controller for wallet service

import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.model.WalletTransaction;
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
//        Order order = orderService.
//        Wallet wallet = walletService.payOrderPayment(order,user)

//        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
        return null;
    }


}
