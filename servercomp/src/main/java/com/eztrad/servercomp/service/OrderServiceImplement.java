package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.OrderStatus;
import com.eztrad.servercomp.domain.OrderType;
import com.eztrad.servercomp.model.*;
import com.eztrad.servercomp.repository.OrderItemRepository;
import com.eztrad.servercomp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Step 74 - order service implementation
// Step 75 - before it go and create OrderRepository and come back
@Service
public class OrderServiceImplement implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // belongs to step 87
    @Autowired
    private AssetService assetService;


    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity(); // * or .

        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order gerOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    // Step 76 - Before Move on the process order we have to create the createOrderItem method on OrderItem class
    // Step 77 - Before this go and create OrderItemRepository and come

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }


    // Step 78 - buy the asset picking from step 76

    @Transactional     // Transactional means if only all the stage proceed successfully it go next action otherwise transaction will be decline for all
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if(quantity<=0){
            throw new Exception("quantity should be > 0");
        }
        double buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);
        Order order = createOrder(user, orderItem, OrderType.BUY);

        walletService.payOrderPayment(order,user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        // Create asset part is skipped for now will come later
        // Step 87 - this will continue after step 86 - asset controller

        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(
                order.getUser().getId(),
                order.getOrderItem().getCoin().getId());

        if(oldAsset==null){
            assetService.createAsset(user,orderItem.getCoin(),orderItem.getQuantity());
        }else {
            assetService.updateAsset(oldAsset.getId(),quantity);
        }

        // the above only belongs to step 87

        return savedOrder;
    }


    // Step 79 - sell the asset
    // at the stage we fully coded ,but we didn't fix the bugs so we will come later whether the asset service is created
    @Transactional
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
        if(quantity<=0){
            throw new Exception("quantity should be > 0");
        }
        double sellPrice = coin.getCurrentPrice();

        // Step 88 - this will continue after step 87

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());

        if(assetToSell != null){

            double buyPrice = assetToSell.getBuyPrice();
            OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);

            if(assetToSell.getQuantity()>=quantity){
                order.setStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order savedOrder = orderRepository.save(order);

                walletService.payOrderPayment(order,user);

                Asset updatedAsset = assetService.updateAsset(assetToSell.getId(),-quantity);

                if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return savedOrder;
            }
            throw new Exception("Insufficient quantity to sell");

        }
        throw new Exception("asset not found");

        // the above only belongs to step 88
        // then go to Wallet controller and work with step - 89

    }



    // this is also the continuation of step 78/79 to code but created at step 74
    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin,quantity,user);
        }
        else if (orderType.equals(OrderType.SELL)){
            return sellAsset(coin,quantity,user);
        }
        throw new Exception("invalid order type");
    }

    // Step 80 - Now next move on to create the controller for this - OrderController
}
