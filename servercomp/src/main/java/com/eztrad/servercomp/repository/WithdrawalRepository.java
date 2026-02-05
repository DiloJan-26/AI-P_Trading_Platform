package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// step 94 - withdrawal repository is created
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findByUserId(Long userId);
}
