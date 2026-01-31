package com.eztrad.servercomp.service;


import com.eztrad.servercomp.model.VerificationCode;

// step 46 - verification code service
public interface VerificationCodeService {

        VerificationCode sendVerificationCode(VerificationCode verificationCode);
        VerificationCode getVerificationCodeById(Long id);
        VerificationCode getVerificationCodeByUser(Long userId);

        void deleteVerificationCodeById(VerificationCode verificationCode);
}
