package com.eztrad.chatbotserver.controller;

// ============================================================================
// STEP 9 - CHATBOT CONTROLLER
// ============================================================================
// This controller handles all chat-related REST API endpoints:
// 1. POST /ai/chat - Fetch real-time crypto market data
// 2. POST /ai/chat/simple - Send prompts to Gemini AI
//
// EXECUTION FLOW:
// Client → HTTP Request → ChatbotController Method
//          ↓
//       ChatbotService Method
//          ↓
//       External API (CoinGecko or Gemini)
//          ↓
//       Response → Client
// ============================================================================

import com.eztrad.chatbotserver.dto.ChatRequest;
import com.eztrad.chatbotserver.dto.ChatResponse;
import com.eztrad.chatbotserver.dto.Coin;
import com.eztrad.chatbotserver.dto.PromptBody;
import com.eztrad.chatbotserver.response.ApiResponse;
import com.eztrad.chatbotserver.service.ChatbotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/chat")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    // ========================================================================
    // STEP 9.1 - ENDPOINT: Fetch Crypto Market Data
    // ========================================================================
    // URL: POST http://localhost:5454/ai/chat
    // Content-Type: application/json
    //
    // REQUEST BODY:
    // {
    //   "prompt": "bitcoin"
    // }
    //
    // PROCESS:
    // 1. Receive JSON request with "prompt" field
    // 2. Call ChatbotService.getCoinDetails(prompt)
    // 3. Service resolves coin ID from prompt
    // 4. Service fetches market data from CoinGecko
    // 5. Return ApiResponse with Coin object
    //
    // RESPONSE:
    // {
    //   "message": "Coin data fetched successfully",
    //   "data": {
    //     "id": "bitcoin",
    //     "currentPrice": 45250.50,
    //     "marketCap": 885000000000,
    //     ... (other coin fields)
    //   }
    // }
    //
    // VALID PROMPTS: "bitcoin", "ethereum", "btc", "eth", "what is doge"
    // ========================================================================
    @PostMapping
    public ResponseEntity<ApiResponse> getCoinDetails(@RequestBody PromptBody prompt) throws Exception {
        ApiResponse response = chatbotService.getCoinDetails(prompt.getPrompt());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ========================================================================
    // STEP 12 - ENDPOINT: AI Chat (JSON Format)
    // ========================================================================
    // URL: POST http://localhost:5454/ai/chat/simple
    // Content-Type: application/json
    //
    // REQUEST BODY:
    // {
    //   "prompt": "What is blockchain technology?"
    // }
    //
    // PROCESS:
    // 1. Receive JSON request with "prompt" field
    // 2. Call ChatbotService.simpleChat(prompt)
    // 3. Service formats prompt and sends to Gemini API
    // 4. Gemini processes and returns AI response
    // 5. Return raw Gemini JSON response
    //
    // GEMINI RESPONSE STRUCTURE:
    // {
    //   "candidates": [
    //     {
    //       "content": {
    //         "parts": [
    //           {
    //             "text": "Blockchain is a distributed ledger technology..."
    //           }
    //         ]
    //       },
    //       "finishReason": "STOP"
    //     }
    //   ],
    //   "usageMetadata": {
    //     "promptTokenCount": 10,
    //     "candidatesTokenCount": 85
    //   }
    // }
    //
    // VALID PROMPTS: Any question or statement (e.g., "Explain Bitcoin",
    //                "What is DeFi?", "How does blockchain work?")
    // ========================================================================
    @PostMapping(value = "/simple", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> simpleChatHandler(@RequestBody PromptBody prompt) {
        String response = chatbotService.simpleChat(prompt.getPrompt());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ========================================================================
    // STEP 12.1 - ENDPOINT: AI Chat (Plain Text Format)
    // ========================================================================
    // URL: POST http://localhost:5454/ai/chat/simple
    // Content-Type: text/plain
    //
    // REQUEST BODY (raw text):
    // What is the difference between Bitcoin and Ethereum?
    //
    // PROCESS:
    // 1. Receive plain text request
    // 2. Call ChatbotService.simpleChat(prompt)
    // 3. Same process as JSON endpoint above
    // 4. Return Gemini response as JSON
    //
    // NOTE: Same endpoint path but different Content-Type
    //       Spring automatically routes to correct handler method
    //
    // ADVANTAGES:
    // - Accept plain text input without JSON wrapping
    // - More flexible for simple text queries
    // - Response is still JSON format
    // ========================================================================
    @PostMapping(value = "/simple", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> simpleChatHandlerPlain(@RequestBody String prompt) {
        String response = chatbotService.simpleChat(prompt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ========================================================================
    // STEP 13 - ENDPOINT: Combined Crypto AI Chat (Crypto + Generic Questions)
    // ========================================================================
    // URL: POST http://localhost:5454/ai/chat/cryptoai
    // Content-Type: application/json
    //
    // PURPOSE:
    // This endpoint combines both CoinGecko (market data) and Gemini (AI)
    // in a single request. It intelligently detects:
    //   - CRYPTO QUESTIONS: "What is the current price of bitcoin?"
    //     → Fetches real-time data from CoinGecko
    //     → Injects data context into Gemini prompt
    //     → Returns AI-generated answer with market data
    //
    //   - GENERIC QUESTIONS: "What means blockchain?"
    //     → Skips CoinGecko (no market data needed)
    //     → Sends directly to Gemini AI
    //     → Returns educational answer
    //
    // REQUEST BODY:
    // {
    //   "question": "what is the current price of bitcoin?"
    // }
    //
    // RESPONSE (CRYPTO QUESTION):
    // {
    //   "userQuestion": "what is the current price of bitcoin?",
    //   "aiAnswer": "Bitcoin is currently trading at $45,250... [AI generated response]",
    //   "cryptoData": "{\"id\":\"bitcoin\",\"currentPrice\":45250,...}",
    //   "questionType": "crypto"
    // }
    //
    // RESPONSE (GENERIC QUESTION):
    // {
    //   "userQuestion": "what means blockchain?",
    //   "aiAnswer": "Blockchain is a distributed ledger technology...",
    //   "cryptoData": null,
    //   "questionType": "generic"
    // }
    //
    // WORKFLOW:
    // 1. Receive user question in JSON format
    // 2. Analyze question to detect if crypto-related
    // 3. IF crypto question:
    //    a. Extract crypto name from question (e.g., "bitcoin" from "price of bitcoin")
    //    b. Call getCoinDetails() to fetch market data from CoinGecko
    //    c. Combine market data with user question as Gemini context
    //    d. Send enhanced prompt to Gemini AI
    //    e. Return response with crypto data + AI answer
    // 4. IF generic question:
    //    a. Skip CoinGecko API call
    //    b. Send question directly to Gemini AI
    //    c. Return response with null crypto data
    //
    // CRYPTO KEYWORDS DETECTED:
    // bitcoin, ethereum, cardano, ripple, tether, solana, polkadot,
    // dogecoin, litecoin, monero, crypto, blockchain, price, market cap,
    // btc, eth, coin, token, trading, exchange, wallet, hodl
    //
    // EXAMPLE CRYPTO QUESTIONS:
    // - "what is the current price of bitcoin?"
    // - "how much is ethereum worth?"
    // - "show me cardano market cap"
    // - "what's the price of btc?"
    // - "is doge a good investment?"
    //
    // EXAMPLE GENERIC QUESTIONS:
    // - "what means blockchain?"
    // - "explain smart contracts"
    // - "what is DeFi?"
    // - "how does cryptocurrency work?"
    // - "what is web3?"
    // ========================================================================
    @PostMapping(value = "/cryptoai", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cryptoAiChat(@RequestBody ChatRequest request) {
        try {
            // Step 13.1 - Detect if question is crypto-related
            boolean isCryptoQuestion = isCryptoRelated(request.getQuestion());

            String aiResponse;
            String cryptoDataJson = null;

            if (isCryptoQuestion) {
                // Step 13.2 - CRYPTO QUESTION PATH
                // Extract crypto name from question
                String cryptoName = extractCryptoName(request.getQuestion());

                // Fetch market data from CoinGecko via getCoinDetails
                ApiResponse coinResponse = chatbotService.getCoinDetails(cryptoName);

                // Convert coin data to JSON string for context injection
                Object coinData = coinResponse.getData();
                if (coinData instanceof Coin coin) {
                    cryptoDataJson = chatbotService.coinToJson(coin);
                } else {
                    throw new IllegalStateException("Unexpected coin data type: " +
                            (coinData == null ? "null" : coinData.getClass().getName()));
                }

                // Step 13.3 - Enhance prompt with market data context
                String enhancedPrompt = chatbotService.enhancePromptWithCryptoData(
                        request.getQuestion(),
                        cryptoDataJson
                );

                // Step 13.4 - Get AI response with crypto context
                aiResponse = chatbotService.simpleChat(enhancedPrompt);

                // Extract clean text from Gemini JSON response
                aiResponse = chatbotService.extractGeminiText(aiResponse);

            } else {
                // Step 13.5 - GENERIC QUESTION PATH
                // Send directly to Gemini without market data
                String response = chatbotService.simpleChat(request.getQuestion());

                // Extract clean text from Gemini JSON response
                aiResponse = chatbotService.extractGeminiText(response);
            }

            // Step 13.6 - Build and return unified response
            ChatResponse chatResponse = new ChatResponse(
                    request.getQuestion(),
                    aiResponse,
                    cryptoDataJson,
                    isCryptoQuestion ? "crypto" : "generic"
            );

            return ResponseEntity.ok(chatResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ========================================================================
    // STEP 13.1 - HELPER METHOD: Detect if Question is Crypto-Related
    // ========================================================================
    // Purpose: Analyze user question to determine if it's about crypto/finance
    // Returns: true if question contains crypto keywords, false otherwise
    // ========================================================================
    private boolean isCryptoRelated(String question) {
        String[] cryptoKeywords = {
                "bitcoin", "ethereum", "cardano", "ripple", "tether", "solana",
                "polkadot", "dogecoin", "litecoin", "monero", "xrp", "ada",
                "crypto", "blockchain", "price", "market cap", "btc", "eth",
                "coin", "token", "trading", "exchange", "wallet", "hodl",
                "defi", "nft", "staking", "mining", "transaction"
        };

        String lowerQuestion = question.toLowerCase();

        for (String keyword : cryptoKeywords) {
            if (lowerQuestion.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    // ========================================================================
    // STEP 13.2 - HELPER METHOD: Extract Crypto Name from Question
    // ========================================================================
    // Purpose: Parse question to find specific cryptocurrency mentioned
    // Example: "what is bitcoin price?" → "bitcoin"
    // Returns: Cryptocurrency name or "bitcoin" (default)
    // ========================================================================
    private String extractCryptoName(String question) {
        String[] cryptos = {
                "bitcoin", "ethereum", "cardano", "ripple", "tether",
                "solana", "polkadot", "dogecoin", "litecoin", "monero",
                "xrp", "ada", "btc", "eth", "doge"
        };

        String lowerQuestion = question.toLowerCase();

        for (String crypto : cryptos) {
            if (lowerQuestion.contains(crypto)) {
                return crypto;
            }
        }
        return "bitcoin"; // default fallback
    }
};

