package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.Coin;

import java.util.List;

// step 58 - coin service
public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String coinId, int days);

    String getCoinDetails(String coinId);

    Coin findById(String coinId);

    String searchCoin(String keyword);

    String getTop50CoinsByMarketCapRank();

    String getTradingCoins();

}
