package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.Coin;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.model.Watchlist;
import com.eztrad.servercomp.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Step 98 - watchlist service implementation
// Step 99 - first go and create Watchlist repository and come back
@Service
public class WatchlistServiceImplement implements WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if(watchlist==null){
            throw new Exception("watchlist not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> optionalWatchlist = watchlistRepository.findById(id);
        if(optionalWatchlist.isEmpty()){
            throw new Exception("watchlist not found");
        }
        return optionalWatchlist.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());

        if(watchlist.getCoins().contains(coin)){
            watchlist.getCoins().remove(coin);
        } else {
            watchlist.getCoins().add(coin);
        }
        watchlistRepository.save(watchlist);
        return coin;
    }

    // now go and create the controller for this
    // step 100 - WatchListController
}
