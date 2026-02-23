package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.WalletTransactionType;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.model.WalletTransaction;

import java.util.List;

// Step 116 - Service for managing wallet transactions and transaction history
public interface TransactionService {

    // Create a new wallet transaction
    WalletTransaction createTransaction(Wallet wallet,
                                       WalletTransactionType type,
                                       String transferId,
                                       String purpose,
                                       Long amount);

    // Get all transactions for a specific wallet
    List<WalletTransaction> getTransactionsByWallet(Wallet wallet);

    // Get all transactions by wallet ID
    List<WalletTransaction> getTransactionsByWalletId(Long walletId);

    // Get transactions by transfer ID (for wallet-to-wallet transfers)
    List<WalletTransaction> getTransactionsByTransferId(String transferId);
}
