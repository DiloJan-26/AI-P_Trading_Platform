package com.eztrad.servercomp.model;

import jakarta.persistence.*;
import lombok.Data;

// step 82 - Asset model for asset objects
@Entity
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;
    private double buyPrice;

    @ManyToOne
    private Coin coin;

    @ManyToOne
    private User user;

    // Step 83 - create Service for it -> AssetService

}
