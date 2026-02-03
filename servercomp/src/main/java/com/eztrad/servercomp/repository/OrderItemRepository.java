package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 77 - repo created for store ordered item
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
