package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.OrderType;
import com.eztrad.servercomp.model.Order;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Wallet;
import com.eztrad.servercomp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

// Step 65 - wallet service implementation
// Step 66 - go and create a WalletRepository and come back
@Service
public class WalletServiceImplement implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet==null){
            wallet = new Wallet();
            wallet.setUser(user);
        }

        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
        wallet.setBalance(newBalance);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet recieverWallet, Long amount) throws Exception {
        Wallet senderwallet = getUserWallet(sender);

        if(senderwallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("Insufficient Balance...");
        }

        BigDecimal senderBalance = senderwallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderwallet.setBalance(senderBalance);
        walletRepository.save(senderwallet);

        BigDecimal receiverBalance = recieverWallet.getBalance().add(BigDecimal.valueOf(amount));
        recieverWallet.setBalance(receiverBalance);
        walletRepository.save(recieverWallet);
        return senderwallet;
    }

    // Step 67 - go and create Order model and come back

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {

        Wallet wallet = getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());

            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient fun for this transaction");
            }
            wallet.setBalance(newBalance);
        }
        else {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        return wallet;
    }

    // Step 71 - Move to Create WalletController
}
