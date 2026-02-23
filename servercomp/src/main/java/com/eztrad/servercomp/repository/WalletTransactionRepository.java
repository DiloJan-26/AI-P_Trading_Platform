package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Step 116 - Repository for WalletTransaction to manage transaction history
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    // Find all transactions for a specific wallet
    List<WalletTransaction> findByWalletOrderByDateDesc(Wallet wallet);

    // Find transactions by wallet ID
    List<WalletTransaction> findByWalletIdOrderByDateDesc(Long walletId);

    // Find transaction by transfer ID (for wallet-to-wallet transfers)
    List<WalletTransaction> findByTransferId(String transferId);
}
