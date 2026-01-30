package com.eztrad.servercomp.utils;

import java.util.Random;

// step 36 - otp_utils created - generate otp for us and convert into string
public class OtpUtils {
    public static String generateOTP(){
        int otpLength = 6;
        Random random = new Random();

        StringBuilder otp = new StringBuilder(otpLength);

        for(int i=0; i<otpLength; i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
