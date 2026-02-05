package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.PaymentDetails;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// step 103 - PaymentDetailsService Implementation
// Step 104 - go and create the repo for it
@Service
public class PaymentDetailsServiceImplement implements PaymentDetailsService{

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String IFSC, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();

        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIFSC(IFSC);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);

        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepository.findByUserId(user.getId());
    }

    // Step 105 - now go and create the controller - PaymentDetailsController
}
