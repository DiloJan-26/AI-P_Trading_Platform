package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

//Step 44 - verification code object
@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private User user;

    private String email;
    private String mobile;
    private VerificationType verificationType;
}
