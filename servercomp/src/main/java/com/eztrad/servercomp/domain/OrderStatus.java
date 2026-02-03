package com.eztrad.servercomp.domain;

// Step 69 - domain for order status
public enum OrderStatus {
    PENDING,
    FAILED,
    CANCELLED,
    PARTIALLY_FAILED,
    ERROR,
    SUCCESS
}
