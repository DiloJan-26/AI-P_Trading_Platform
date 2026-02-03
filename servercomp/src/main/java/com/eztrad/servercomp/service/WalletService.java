package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Wallet;

// step 62 - service creation for wallet
// step 63 - first go to create the Wallet model and come back
// step 64 - also go and create the Order model and come
public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id);
    Wallet walletToWalletTransfer(User user, Wallet recieverWallet, Long amount);
    Wallet payOrderPayment(Order order, User user);

}
