package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

// Step 72 - model for wallet transaction
@Entity
@Data
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    // step 73 - go and create the WalletTransactionType domain and come

    private WalletTransactionType walletTransactionType;

    private LocalDate date;

    private String transferId; // for buy and sell transferId = null ,  transfer id only come when wallet to wallet transfer

    private String purpose;

    private Long amount;

}
