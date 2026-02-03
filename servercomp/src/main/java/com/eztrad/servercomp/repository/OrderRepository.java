package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Step 75 - order repository created
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

}
