package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.service.CoinService;
import com.eztrad.servercomp.service.OrderService;
import com.eztrad.servercomp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Step 80 - controller for ordering
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;
//






}
