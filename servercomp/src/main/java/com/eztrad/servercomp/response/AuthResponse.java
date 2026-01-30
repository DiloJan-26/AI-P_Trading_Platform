package com.eztrad.servercomp.response;

import lombok.Data;

// step 27 - regarding to @PostMapping ("/signup") at AuthController AuthResponse
@Data
public class AuthResponse {
    private String jwt;
    private Boolean status;
    private String message;
    private Boolean isTwoFactorAuthEnabled;
    private String session;
}
