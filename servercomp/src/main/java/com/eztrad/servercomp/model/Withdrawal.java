package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// Step 90 - Withdrawal model class
// step 91 - create WithdrawalStatus domain and come back
@Entity
@Data
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawalStatus status;
    private Long amount;

    @ManyToOne
    private User user;

    private LocalDateTime date = LocalDateTime.now();

    // Step 92 - now go to create a service - WithdrawalService
}
