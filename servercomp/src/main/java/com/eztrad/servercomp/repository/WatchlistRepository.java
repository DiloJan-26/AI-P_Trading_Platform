package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 99 - watchlist repo
public interface WatchlistRepository extends JpaRepository<Watchlist, Long>  {

    Watchlist findByUserId(Long userId);
}
