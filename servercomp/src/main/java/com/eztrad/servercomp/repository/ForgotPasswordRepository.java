package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 52 - repo for forgot password
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

    ForgotPasswordToken findByUserId(Long userId);
}
