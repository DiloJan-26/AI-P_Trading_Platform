package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 104 - PaymentDetails repo created
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

    PaymentDetails findByUserId(Long userId);

}
