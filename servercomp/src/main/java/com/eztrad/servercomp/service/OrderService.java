package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.OrderType;
import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.OrderItem;
import com.eztrad.servercomp.model.User;
import org.aspectj.weaver.ast.Or;

import java.util.List;

// Step 73 - Service created for ordering
public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order gerOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUsers(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

    // Step 74 - go and create and write Implementation for it - OrderServiceImplement
}
