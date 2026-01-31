package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 45 - repo for verification codes
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    public VerificationCode findByUserId(Long userId);

}
