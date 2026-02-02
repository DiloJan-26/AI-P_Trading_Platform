package com.eztrad.servercomp.request;

import lombok.Data;

// step 56 - Reset password request created
@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}