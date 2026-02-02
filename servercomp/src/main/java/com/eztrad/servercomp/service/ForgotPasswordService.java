package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.VerificationType;
import com.eztrad.servercomp.model.ForgotPasswordToken;
import com.eztrad.servercomp.model.User;

// Step 51 - forgot password service
public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user,
                                    String id, String otp,
                                    VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);

}
