package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.PaymentDetails;
import com.eztrad.servercomp.model.User;

// step 102 - PaymentDetailsService
public interface PaymentDetailsService {

    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String IFSC,
                                            String bankName,
                                            User user);

    public PaymentDetails getUsersPaymentDetails(User user);

    // step 103 - go for implementation
}
