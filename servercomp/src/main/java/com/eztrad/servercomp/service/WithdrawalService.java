package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Withdrawal;

import java.util.List;

// Step 92 - withdrawal service
public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();

}
