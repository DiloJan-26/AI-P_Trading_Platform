package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

// Step 50 - model for forgot password object
@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;

    private String otp;
    private VerificationType verificationType;
    private String sendTo;
}
