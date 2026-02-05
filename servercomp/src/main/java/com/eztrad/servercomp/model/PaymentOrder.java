package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.PaymentMethod;
import com.eztrad.servercomp.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

// Step 106 - create PaymentOrder model
// Step 107 - before go and create a domain for PaymentOrderStatus
@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;


    // Step 109 - go and create PaymentService

}
