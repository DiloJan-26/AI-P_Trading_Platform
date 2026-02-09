package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Watchlist;
import com.eztrad.servercomp.model.Withdrawal;
import com.eztrad.servercomp.service.CoinService;
import com.eztrad.servercomp.service.UserService;
import com.eztrad.servercomp.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Step 100 - watchList Controller
@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());

        return ResponseEntity.ok(watchlist);
    }


    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistById(
            @PathVariable Long watchlistId
    ) throws Exception {
        Watchlist watchlist = watchlistService.findById(watchlistId);

        return ResponseEntity.ok(watchlist);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
            @RequestHeader("Authentication") String jwt,
            @PathVariable String coinId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchlist(coin,user);

        return ResponseEntity.ok(addedCoin);
    }

    // now go and create api for payment details
    // Step 101 - PaymentDetails model

}
