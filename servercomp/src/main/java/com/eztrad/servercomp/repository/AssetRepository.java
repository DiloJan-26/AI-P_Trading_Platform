package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// step 84 - asset repo created
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByUserId(Long userId);

    Asset findByUserIdAndCoinId(Long userId, String coinId);

    // next step 85 -- go for service implementation
}
