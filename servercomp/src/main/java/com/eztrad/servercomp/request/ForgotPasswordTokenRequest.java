package com.eztrad.servercomp.request;

import com.eztrad.servercomp.domain.VerificationType;
import lombok.Data;

// step 55 - forgot Password Token Request created
@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
