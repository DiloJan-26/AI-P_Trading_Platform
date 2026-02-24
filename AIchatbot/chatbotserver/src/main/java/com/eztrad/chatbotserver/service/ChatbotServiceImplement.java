package com.eztrad.chatbotserver.service;

// Step 6 - create a service implementation to implement the chatbot service methods
import com.eztrad.chatbotserver.dto.Coin;
import com.eztrad.chatbotserver.response.ApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class ChatbotServiceImplement implements ChatbotService{

    String GEMINI_API_KEY="Gemini API Key here";

    // Step 8.1 - implement a helper method to convert Object to double, since the API response can contain different types of numeric values
    private double convertToDouble(Object value) {
        return switch (value) {
            case Integer i -> i.doubleValue();
            case Long l -> l.doubleValue();
            case Double d -> d;
            default -> throw new IllegalArgumentException("Unsupported type: " + value.getClass().getName());
        };
    }

    // Step 8 - implement the method to make an API request to CoinGecko and return the coin details
    @SuppressWarnings("unchecked")
    public Coin makeApiRequest(String currencyName) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/bitcoin";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.getForEntity(url, (Class<Map<String, Object>>) (Class<?>) Map.class);
        Map<String, Object> responseBody = responseEntity.getBody();

        if(responseBody != null){
            Map<String, Object> marketData = (Map<String, Object>) responseBody.get("market_data");
            Map<String, Object> image = (Map<String, Object>) responseBody.get("image");

            Coin coin = new Coin();
            coin.setId((String) responseBody.get("id"));
            coin.setSymbol((String) responseBody.get("symbol"));
            coin.setName((String) responseBody.get("name"));
            coin.setImage((String) image.get("large"));

            //Step 8.2 -  Market data
            coin.setCurrentPrice(convertToDouble(((Map<String, Object>) marketData.get("current_price")).get("usd")));
            coin.setMarketCap((long) convertToDouble(((Map<String, Object>) marketData.get("market_cap")).get("usd")));

            coin.setMarketCapRank((Integer) marketData.get("market_cap_rank"));
            coin.setFullyDilutedValuation((long) convertToDouble(((Map<String, Object>) marketData.get("fully_diluted_valuation")).get("usd")));
            coin.setTotalVolume((long) convertToDouble(((Map<String, Object>) marketData.get("total_volume")).get("usd")));

            coin.setHigh24h(convertToDouble(((Map<String, Object>) marketData.get("high_24h")).get("usd")));
            coin.setLow24h(convertToDouble(((Map<String, Object>) marketData.get("low_24h")).get("usd")));
            coin.setPriceChange24h(convertToDouble(( marketData.get("price_change_24h"))));

            coin.setPriceChangePercentage24h(convertToDouble(marketData.get("price_change_percentage_24h")));
            coin.setMarketCapChange24h((long) convertToDouble(( marketData.get("market_cap_change_24h"))));
            coin.setMarketCapChangePercentage24h((long) convertToDouble(marketData.get("market_cap_change_percentage_24h")));
            coin.setCirculatingSupply(convertToDouble(marketData.get("circulating_supply")));
            coin.setTotalSupply((long) convertToDouble(marketData.get("total_supply")));

            return coin;


        }
        throw new Exception("Failed to fetch coin details from CoinGecko API");
    }


    @Override
    public ApiResponse getCoinDetails(String prompt) throws Exception {
        Coin coin = makeApiRequest(prompt);
        System.out.println("coin dto ---" + coin);
        return null;
    }

    // Step 11 - implement the method to make an API request to Gemini and return the chatbot response
    @Override
    public String simpleChat(String prompt) {
        String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", prompt)))
                        )
                ).toString();

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL, requestEntity, String.class);

        return response.getBody();
    }

}
