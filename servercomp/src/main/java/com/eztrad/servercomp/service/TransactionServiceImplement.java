package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.WalletTransactionType;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.model.WalletTransaction;
import com.eztrad.servercomp.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// Step 116 - Implementation of TransactionService for wallet transaction management
@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public WalletTransaction createTransaction(Wallet wallet,
                                              WalletTransactionType type,
                                              String transferId,
                                              String purpose,
                                              Long amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setWalletTransactionType(type);
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());

        return walletTransactionRepository.save(transaction);
    }

    @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
        return walletTransactionRepository.findByWalletOrderByDateDesc(wallet);
    }

    @Override
    public List<WalletTransaction> getTransactionsByWalletId(Long walletId) {
        return walletTransactionRepository.findByWalletIdOrderByDateDesc(walletId);
    }

    @Override
    public List<WalletTransaction> getTransactionsByTransferId(String transferId) {
        return walletTransactionRepository.findByTransferId(transferId);
    }
}
