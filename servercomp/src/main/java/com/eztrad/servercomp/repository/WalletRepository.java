package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 66 - wallet repo created
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);

}
