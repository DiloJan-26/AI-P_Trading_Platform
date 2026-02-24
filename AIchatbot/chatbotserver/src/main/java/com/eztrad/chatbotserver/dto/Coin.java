package com.eztrad.chatbotserver.dto;

// ============================================================================
// STEP 7 - COIN DATA MODEL (DTO)
// ============================================================================
// This class represents cryptocurrency market data from CoinGecko API.
// It is both a JPA Entity (for database persistence) and a DTO (for API responses).
//
// DATA SOURCE: CoinGecko API (/coins/{id} endpoint)
// USAGE: Response payload for /ai/chat endpoint
// JSON MAPPING: Jackson automatically maps API response to this POJO
// ============================================================================

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Coin {

    // ========================================================================
    // BASIC COIN INFORMATION
    // ========================================================================

    @Id
    @JsonProperty("id")
    // Step 7.1 - Unique coin identifier (used as database key)
    // Example: "bitcoin", "ethereum", "cardano"
    private String id;

    @JsonProperty("symbol")
    // Step 7.2 - Trading symbol (ticker)
    // Example: "btc", "eth", "ada"
    private String symbol;

    @JsonProperty("name")
    // Step 7.3 - Full coin name
    // Example: "Bitcoin", "Ethereum", "Cardano"
    private String name;

    @JsonProperty("image")
    // Step 7.4 - URL to coin logo image
    private String image;

    // ========================================================================
    // PRICE INFORMATION
    // ========================================================================

    @JsonProperty("current_price")
    // Step 7.5 - Current price in USD
    // Example: 45250.50
    private Double currentPrice;

    @JsonProperty("high_24h")
    // Step 7.6 - Highest price in last 24 hours (USD)
    // Example: 46200.75
    private Double high24h;

    @JsonProperty("low_24h")
    // Step 7.7 - Lowest price in last 24 hours (USD)
    // Example: 44800.25
    private Double low24h;

    // ========================================================================
    // MARKET CAPITALIZATION DATA
    // ========================================================================

    @JsonProperty("market_cap")
    // Step 7.8 - Total market cap in USD
    // Example: 885000000000 (885 billion)
    private Long marketCap;

    @JsonProperty("market_cap_rank")
    // Step 7.9 - Ranking by market cap
    // Example: 1 (Bitcoin is #1)
    private Integer marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    // Step 7.10 - Fully diluted valuation in USD
    // (market cap if all coins were in circulation)
    private Long fullyDilutedValuation;

    // ========================================================================
    // TRADING VOLUME
    // ========================================================================

    @JsonProperty("total_volume")
    // Step 7.11 - Total trading volume in last 24h (USD)
    // Example: 35000000000 (35 billion)
    private Long totalVolume;

    // ========================================================================
    // 24-HOUR CHANGES
    // ========================================================================

    @JsonProperty("price_change_24h")
    // Step 7.12 - Price change amount in USD (24h)
    // Example: 1250.75 (positive = increase)
    private Double priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    // Step 7.13 - Price change percentage (24h)
    // Example: 2.85 (means +2.85%)
    private Double priceChangePercentage24h;

    @JsonProperty("market_cap_change_24h")
    // Step 7.14 - Market cap change in USD (24h)
    private Long marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    // Step 7.15 - Market cap change percentage (24h)
    private Long marketCapChangePercentage24h;

    // ========================================================================
    // SUPPLY INFORMATION
    // ========================================================================

    @JsonProperty("circulating_supply")
    // Step 7.16 - Number of coins currently in circulation
    // Example: 21000000 (Bitcoin has fixed supply)
    private Double circulatingSupply;

    @JsonProperty("total_supply")
    // Step 7.17 - Total number of coins that will ever exist
    // Example: 21000000 (Bitcoin max supply)
    private Long totalSupply;

    @JsonProperty("max_supply")
    // Step 7.18 - Maximum coins that will ever be created
    // (null if unlimited)
    private Double maxSupply;

    // ========================================================================
    // ALL-TIME HIGH/LOW DATA
    // ========================================================================

    @JsonProperty("ath")
    // Step 7.19 - All-time high price (USD)
    private long ath;

    @JsonProperty("ath_change_percentage")
    // Step 7.20 - Change from all-time high (percentage)
    // (negative = below ATH)
    private long athChangePercentage;

    @JsonProperty("ath_date")
    // Step 7.21 - Date of all-time high
    private Date athDate;

    @JsonProperty("atl")
    // Step 7.22 - All-time low price (USD)
    private Double atl;

    @JsonProperty("atl_change_percentage")
    // Step 7.23 - Change from all-time low (percentage)
    // (positive = above ATL)
    private long atlChangePercentage;

    @JsonProperty("atl_date")
    // Step 7.24 - Date of all-time low
    private Date atlDate;

    // ========================================================================
    // OTHER FIELDS
    // ========================================================================

    @JsonProperty("roi")
    @JsonIgnore
    // Step 7.25 - Return on Investment (ignored in API response)
    private Double roi;

    @JsonProperty("last_updated")
    // Step 7.26 - Timestamp of last data update
    private Date lastUpdated;
}
