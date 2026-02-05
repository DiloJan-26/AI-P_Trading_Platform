package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.model.Withdrawal;
import com.eztrad.servercomp.service.UserService;
import com.eztrad.servercomp.service.WalletService;
import com.eztrad.servercomp.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Step 95 - create the withdrawal controller
@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authentication") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

        // wallet transaction related code come in place later


        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }


    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable Boolean accept,
            @RequestHeader("Authentication") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(id,accept);

        Wallet userWallet = walletService.getUserWallet(user);
        if(!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }


    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authentication") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getUsersWithWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }


    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalRequest(
            @RequestHeader("Authentication") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    // so our withdrawal api is completed now so now we hove to create the api for watch list
    // Step 96 - create model for Watchlist

}
