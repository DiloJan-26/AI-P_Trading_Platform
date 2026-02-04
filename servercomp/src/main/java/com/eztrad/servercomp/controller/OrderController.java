package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.domain.OrderType;
import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.request.CreateOrderRequest;
import com.eztrad.servercomp.service.CoinService;
import com.eztrad.servercomp.service.OrderService;
import com.eztrad.servercomp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    // Step 81 go and create CreateOrderRequest then come back

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPaymant(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwtToken);
        Order order = orderService.gerOrderById(orderId);

        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        } else {
            throw new Exception("you don't have access");
        }
    }


    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrderForUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    ) throws Exception {
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders = orderService.getAllOrdersOfUser(userId,order_type,asset_symbol);
        return ResponseEntity.ok(userOrders);
    }

    // Step 82 - now lets finish our asset service - first create Asset model

}
