package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

// step 33 - 2 factor otp repository
public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP, String> {

    TwoFactorOTP findByUserId(Long userId);
}
