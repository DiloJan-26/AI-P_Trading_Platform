package com.eztrad.servercomp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// Step 96 - watch list model
@Entity
@Data
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Coin> coins = new ArrayList<>();

    // Step 97 - go and create the service for this - WatchlistService
}
