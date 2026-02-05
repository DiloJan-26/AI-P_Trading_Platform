package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.WithdrawalStatus;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Withdrawal;
import com.eztrad.servercomp.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Step 93 - withdrawal service Implementation
// Step 94 - we want withdrawal repository so create one
@Service
public class WithdrawalServiceImplement implements WithdrawalService{

    @Autowired
    private WithdrawalRepository withdrawalRepository;


    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);

        if(withdrawal.isEmpty()){
            throw new Exception("withdrawal not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());

        if(accept){
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        } else {
            withdrawal1.setStatus(WithdrawalStatus.DECLINED);
        }

        return withdrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }

    // Step 95 - so now go and create the controller for this  - WithdrawalController
}
