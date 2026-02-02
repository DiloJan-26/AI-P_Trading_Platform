package com.eztrad.servercomp.service;


import com.eztrad.servercomp.domain.VerificationType;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.VerificationCode;
import com.eztrad.servercomp.repository.VerificationCodeRepository;
import com.eztrad.servercomp.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

// Step 47 - verification code implementation
public class VerificationCodeServiceImplement implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;


    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("verification code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}
