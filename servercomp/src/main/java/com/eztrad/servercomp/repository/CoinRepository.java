package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
