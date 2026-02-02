package com.eztrad.servercomp.service;


import com.eztrad.servercomp.domain.VerificationType;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.VerificationCode;

// step 46 - verification code service
public interface VerificationCodeService {

        VerificationCode sendVerificationCode(User user , VerificationType verificationType);
        VerificationCode getVerificationCodeById(Long id) throws Exception;
        VerificationCode getVerificationCodeByUser(Long userId);

        void deleteVerificationCodeById(VerificationCode verificationCode);
}
