package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.OrderType;
import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.OrderItem;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Step 74 - order service implementation
// Step 75 - before it go and create OrderRepository and come back
@Service
public class OrderServiceImplement implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        return null;
    }

    @Override
    public Order gerOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersOfUsers(Long userId, OrderType orderType, String assetSymbol) {
        return List.of();
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
