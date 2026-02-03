package com.eztrad.servercomp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

// Step 63 - wallet model
@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne   // one to one means one user must have one wallet
    private User user;

    private BigDecimal balance;

}
