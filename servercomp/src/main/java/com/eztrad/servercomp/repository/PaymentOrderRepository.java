package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

// step 112 - payment service repo created
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
}
