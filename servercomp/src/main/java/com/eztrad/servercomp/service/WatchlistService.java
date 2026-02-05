package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Watchlist;

//Step 97 - watchlist service
public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchlist(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;



    // Step 98 - Service Implementation

}
