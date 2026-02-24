package com.eztrad.chatbotserver.service;

// ============================================================================
// STEP 6 - SERVICE IMPLEMENTATION
// ============================================================================
// This class implements ChatbotService interface and contains all business logic
// for fetching crypto market data and communicating with Gemini AI.
//
// EXECUTION FLOW:
// 1. Application starts → ChatbotController receives requests
// 2. POST /ai/chat → getCoinDetails() → resolves coin → fetches market data
// 3. POST /ai/chat/simple → simpleChat() → sends prompt to Gemini
// ============================================================================

import com.eztrad.chatbotserver.dto.Coin;
import com.eztrad.chatbotserver.response.ApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Service
public class ChatbotServiceImplement implements ChatbotService{

    // ========================================================================
    // STEP 5 - API CONFIGURATION FROM application.properties
    // ========================================================================
    // These values are injected from src/main/resources/application.properties
    // Update the properties file with your actual API keys and endpoints
    // ========================================================================

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.base-url}")
    private String geminiApiBaseUrl;

    @Value("${coingecko.api.base-url}")
    private String coinGeckoApiBaseUrl;

    @Value("${coingecko.api.key:}")
    private String coinGeckoApiKey;

    // ========================================================================
    // STEP 8.1 - HELPER METHOD: Convert Object to Double
    // ========================================================================
    // Purpose: Safely convert any numeric type (Integer, Long, Double) to Double
    // This is needed because CoinGecko API may return different numeric types
    // Called by: getNumber() method
    // ========================================================================
    private double convertToDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        // If it's a Map (nested structure), return 0.0 - should be handled by getNumber()
        if (value instanceof Map) {
            return 0.0;
        }
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        // For any other type, return 0.0 instead of throwing exception
        return 0.0;
    }

    // ========================================================================
    // STEP 8.1.0 - HELPER METHOD: Parse Date from String
    // ========================================================================
    // Purpose: Convert ISO 8601 date strings from CoinGecko API to Java Date objects
    // Example: "2021-11-10T14:24:11.849Z" → Date object
    // Returns: Date object or null if parsing fails
    // ========================================================================
    private Date parseDate(Object dateValue) {
        if (dateValue == null) {
            return null;
        }

        if (dateValue instanceof String dateString) {
            try {
                // ISO 8601 format: 2021-11-10T14:24:11.849Z
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                return sdf.parse(dateString);
            } catch (Exception e) {
                // Try alternative format without milliseconds
                try {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    sdf2.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                    return sdf2.parse(dateString);
                } catch (Exception ex) {
                    System.err.println("Failed to parse date: " + dateString);
                    return null;
                }
            }
        }

        return null;
    }

    // ========================================================================
    // STEP 8.1.1 - HELPER METHOD: Extract Number from Nested Map
    // ========================================================================
    // Purpose: Handle both Map<String,Object> and direct numeric values
    // Example: market_data.current_price = {"usd": 45250.50}
    // This method extracts the USD value from the nested structure
    // ========================================================================
    @SuppressWarnings("unchecked")
    private double getNumber(Object valueOrMap, String currencyKey) {
        if (valueOrMap == null) {
            return 0.0;
        }

        if (valueOrMap instanceof Map<?, ?> map) {
            Object nested = map.get(currencyKey);
            if (nested == null) {
                return 0.0;
            }
            return convertToDouble(nested);
        }

        return convertToDouble(valueOrMap);
    }

    // ========================================================================
    // STEP 8.1.2 - HELPER METHOD: Resolve Coin ID from User Prompt
    // ========================================================================
    // Purpose: Convert user input (e.g., "bitcoin", "btc", "what is eth price")
    //          into a valid coin ID for CoinGecko API
    // Process:
    //   1. Call CoinGecko /search endpoint with user query
    //   2. Extract first match coin ID
    //   3. Default to "bitcoin" if no match found
    // Returns: Valid coin ID string (e.g., "bitcoin", "ethereum", etc.)
    // ========================================================================
    @SuppressWarnings("unchecked")
    private String resolveCoinId(String prompt) {
        String query = Optional.ofNullable(prompt).orElse("").trim();
        if (query.isEmpty()) {
            return "bitcoin";
        }

        String searchUrl = UriComponentsBuilder.fromUriString(coinGeckoApiBaseUrl + "/search")
                .queryParam("query", query)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        if (coinGeckoApiKey != null && !coinGeckoApiKey.isBlank()) {
            headers.add("x-cg-pro-api-key", coinGeckoApiKey);
        }

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                searchUrl,
                org.springframework.http.HttpMethod.GET,
                new HttpEntity<>(headers),
                (Class<Map<String, Object>>) (Class<?>) Map.class
        );

        Map<String, Object> body = response.getBody();
        if (body == null) {
            return "bitcoin";
        }

        Object coinsRaw = body.get("coins");
        if (coinsRaw instanceof List<?> coins && !coins.isEmpty()) {
            Object first = coins.get(0);
            if (first instanceof Map<?, ?> firstMap) {
                Object id = firstMap.get("id");
                return Objects.toString(id, "bitcoin");
            }
        }

        return "bitcoin";
    }

    // ========================================================================
    // STEP 8 - MAIN METHOD: Fetch Crypto Market Data from CoinGecko
    // ========================================================================
    // Purpose: Complete workflow to fetch real-time crypto market data
    //
    // Input: prompt (e.g., "bitcoin", "ethereum", "what is btc price")
    //
    // Process Flow:
    //   1. Resolve coin ID using CoinGecko /search endpoint
    //   2. Build URL for CoinGecko /coins/{id} endpoint
    //   3. Make GET request to CoinGecko API
    //   4. Parse response and extract market data
    //   5. Populate Coin DTO object with all fields
    //   6. Return Coin object with complete data
    //
    // Step 8.2 - Market data extraction:
    //   - currentPrice: Direct USD value from price_change_24h
    //   - marketCap: Market capitalization in USD
    //   - marketCapRank: Coin's ranking by market cap
    //   - 24h metrics: Volume, price change, percentage change
    //   - Supply metrics: Circulating and total supply
    //
    // Error Handling: Throws exception if API request fails
    // ========================================================================
    @SuppressWarnings("unchecked")
    public Coin makeApiRequest(String prompt) throws Exception {
        // Step 8.0 - Resolve the coin ID from user input
        String coinId = resolveCoinId(prompt);

        // Step 8.0.1 - Build CoinGecko API URL with query parameters
        String url = UriComponentsBuilder.fromUriString(coinGeckoApiBaseUrl + "/coins/" + coinId)
                .queryParam("localization", "false")
                .queryParam("tickers", "false")
                .queryParam("market_data", "true")
                .queryParam("community_data", "false")
                .queryParam("developer_data", "false")
                .queryParam("sparkline", "false")
                .toUriString();

        // Step 8.0.2 - Prepare REST client and headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        if (coinGeckoApiKey != null && !coinGeckoApiKey.isBlank()) {
            headers.add("x-cg-pro-api-key", coinGeckoApiKey);
        }

        // Step 8.0.3 - Execute GET request to CoinGecko
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                new HttpEntity<>(headers),
                (Class<Map<String, Object>>) (Class<?>) Map.class
        );

        Map<String, Object> responseBody = responseEntity.getBody();

        // Step 8.1 - Parse and extract market data
        if(responseBody != null){
            Map<String, Object> marketData = (Map<String, Object>) responseBody.get("market_data");
            Map<String, Object> image = (Map<String, Object>) responseBody.get("image");

            Coin coin = new Coin();

            // Step 8.1.1 - Extract basic coin info
            coin.setId((String) responseBody.get("id"));
            coin.setSymbol((String) responseBody.get("symbol"));
            coin.setName((String) responseBody.get("name"));
            if (image != null) {
                coin.setImage((String) image.get("large"));
            }

            // Step 8.2 - Extract market data (prices, volumes, changes)
            if (marketData != null) {
                coin.setCurrentPrice(getNumber(marketData.get("current_price"), "usd"));
                coin.setMarketCap((long) getNumber(marketData.get("market_cap"), "usd"));
                coin.setMarketCapRank((Integer) marketData.get("market_cap_rank"));
                coin.setFullyDilutedValuation((long) getNumber(marketData.get("fully_diluted_valuation"), "usd"));
                coin.setTotalVolume((long) getNumber(marketData.get("total_volume"), "usd"));
                coin.setHigh24h(getNumber(marketData.get("high_24h"), "usd"));
                coin.setLow24h(getNumber(marketData.get("low_24h"), "usd"));

                // These fields are direct values, NOT nested in USD map
                coin.setPriceChange24h(convertToDouble(marketData.get("price_change_24h")));
                coin.setPriceChangePercentage24h(convertToDouble(marketData.get("price_change_percentage_24h")));
                coin.setMarketCapChange24h((long) convertToDouble(marketData.get("market_cap_change_24h")));
                coin.setMarketCapChangePercentage24h((long) convertToDouble(marketData.get("market_cap_change_percentage_24h")));
                coin.setCirculatingSupply(convertToDouble(marketData.get("circulating_supply")));
                coin.setTotalSupply((long) convertToDouble(marketData.get("total_supply")));
                coin.setMaxSupply(convertToDouble(marketData.get("max_supply")));

                // Step 8.2.1 - Extract ATH (All-Time High) data with safe date parsing
                coin.setAth((long) getNumber(marketData.get("ath"), "usd"));
                coin.setAthChangePercentage((long) convertToDouble(marketData.get("ath_change_percentage"))); // Direct value

                // Parse ATH date - check if it's nested in a map or direct value
                Object athDateObj = marketData.get("ath_date");
                if (athDateObj instanceof Map) {
                    coin.setAthDate(parseDate(((Map<String, Object>) athDateObj).get("usd")));
                } else {
                    coin.setAthDate(parseDate(athDateObj));
                }

                // Step 8.2.2 - Extract ATL (All-Time Low) data with safe date parsing
                coin.setAtl(getNumber(marketData.get("atl"), "usd"));
                coin.setAtlChangePercentage((long) convertToDouble(marketData.get("atl_change_percentage"))); // Direct value

                // Parse ATL date - check if it's nested in a map or direct value
                Object atlDateObj = marketData.get("atl_date");
                if (atlDateObj instanceof Map) {
                    coin.setAtlDate(parseDate(((Map<String, Object>) atlDateObj).get("usd")));
                } else {
                    coin.setAtlDate(parseDate(atlDateObj));
                }
            }

            // Step 8.2.3 - Extract last_updated timestamp (direct value, not nested)
            coin.setLastUpdated(parseDate(responseBody.get("last_updated")));

            return coin;
        }
        throw new Exception("Failed to fetch coin details from CoinGecko API");
    }


    // ========================================================================
    // STEP 9 - SERVICE METHOD: Get Coin Details
    // ========================================================================
    // Purpose: Public endpoint for controller to fetch coin market data
    // Called by: ChatbotController.getCoinDetails()
    // Input: prompt (coin name, symbol, or natural language)
    // Output: ApiResponse with Coin object containing market data
    // ========================================================================
    @Override
    public ApiResponse getCoinDetails(String prompt) throws Exception {
        Coin coin = makeApiRequest(prompt);

        ApiResponse response = new ApiResponse();
        response.setMessage("Coin data fetched successfully");
        response.setData(coin);

        return response;
    }

    // ========================================================================
    // STEP 11 - SERVICE METHOD: Send Chat Prompt to Gemini
    // ========================================================================
    // Purpose: Send user prompt to Gemini AI and return response
    // Called by: ChatbotController.simpleChatHandler()
    //
    // Process Flow:
    //   1. Build Gemini API URL with API key
    //   2. Set Content-Type header to application/json
    //   3. Build request body with user prompt in Gemini format
    //   4. Send POST request to Gemini API
    //   5. Return raw JSON response from Gemini
    //
    // Gemini Request Format:
    //   {
    //     "contents": [
    //       {
    //         "parts": [
    //           { "text": "user prompt here" }
    //         ]
    //       }
    //     ]
    //   }
    //
    // Input: prompt (any question or statement)
    // Output: JSON string with Gemini response
    // ========================================================================
    @Override
    public String simpleChat(String prompt) {
        String geminiUrl = geminiApiBaseUrl + "?key=" + geminiApiKey;

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
        ResponseEntity<String> response = restTemplate.postForEntity(geminiUrl, requestEntity, String.class);

        return response.getBody();
    }

}
