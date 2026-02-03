package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Step 65 - wallet service implementation
// Step 66 - go and create a WalletRepository and come back
@Service
public class WalletServiceImplement implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        return null;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        return null;
    }

    @Override
    public Wallet findWalletById(Long id) {
        return null;
    }

    @Override
    public Wallet walletToWalletTransfer(User user, Wallet recieverWallet, Long amount) {
        return null;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        return null;
    }
}
