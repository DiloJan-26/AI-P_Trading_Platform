package com.eztrad.servercomp.controller;


import com.eztrad.servercomp.model.Asset;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.service.AssetService;
import com.eztrad.servercomp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Step 86 - asset controller
@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
            @PathVariable String coinId,
            @RequestHeader("Authentication") String jwt
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        return ResponseEntity.ok().body(asset);
    }


    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUser(
            @RequestHeader("Authentication") String jwt
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Asset> assets = assetService.getUsersAsset(user.getId());
        return ResponseEntity.ok().body(assets);
    }

    // Now return to order service implementation to finish the balance

}
