package com.eztrad.chatbotserver.service;

// ============================================================================
// STEP 5 - SERVICE INTERFACE
// ============================================================================
// This interface defines the contract for chatbot service operations.
// Implementation: ChatbotServiceImplement.java
//
// METHODS:
// 1. getCoinDetails(String prompt): Fetch crypto market data from CoinGecko
// 2. simpleChat(String prompt): Send prompt to Gemini and get response
// ============================================================================

import com.eztrad.chatbotserver.response.ApiResponse;

public interface ChatbotService {

    // ========================================================================
    // METHOD 1: Get Coin Details
    // ========================================================================
    // Purpose: Fetch real-time cryptocurrency market data
    // Input: prompt - coin name, symbol, or natural language query
    //        Examples: "bitcoin", "ethereum", "btc", "what is eth"
    // Output: ApiResponse containing Coin object with market data
    // Throws: Exception - if CoinGecko API fails
    // Called by: ChatbotController.getCoinDetails()
    // ========================================================================
    ApiResponse getCoinDetails(String prompt) throws Exception;

    // ========================================================================
    // METHOD 2: Simple Chat
    // ========================================================================
    // Purpose: Send prompt to Gemini AI and get response
    // Input: prompt - any question or statement
    //        Examples: "What is blockchain?", "Explain DeFi"
    // Output: JSON string with Gemini response
    // Called by: ChatbotController.simpleChatHandler()
    // ========================================================================
    String simpleChat(String prompt);
}
