package com.eztrad.servercomp.request;

import com.eztrad.servercomp.domain.OrderType;
import lombok.Data;

// Step 81 - creating order request
@Data
public class CreateOrderRequest {

    private String coinId;
    private double quantity;
    private OrderType orderType;

}
