package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.TwoFactorOTP;
import com.eztrad.servercomp.model.User;

// step 31 - service for 2 factor otp
public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);


}
