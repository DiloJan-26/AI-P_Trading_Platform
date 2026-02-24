package com.eztrad.servercomp.controller;
// Step final - Step 117 - Transaction api part

import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.model.WalletTransaction;
import com.eztrad.servercomp.service.TransactionService;
import com.eztrad.servercomp.service.UserService;
import com.eztrad.servercomp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Step 117 - Controller for transaction history api to fetch user's wallet transactions
@RestController
public class TransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        List<WalletTransaction> transactionList = transactionService.getTransactionsByWallet(wallet);

        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
    }

    // so you finished up all the api by the end of 117th step now on - at 7:48:00
    // go to application.properties
    // since all api are ready then go and check with postman
}
