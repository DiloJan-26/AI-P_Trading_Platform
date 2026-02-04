package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.Asset;
import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.User;

import java.util.List;

// Step 83 - service for Asset management
public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetId) throws Exception;

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUsersAsset(Long userId);

    Asset updateAsset(Long assetId, double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);

    // Step 84 - create the repo for it - AssetRepository
}
