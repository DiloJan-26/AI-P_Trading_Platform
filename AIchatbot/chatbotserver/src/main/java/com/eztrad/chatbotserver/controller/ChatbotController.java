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

    public ChatbotController (ChatbotService chatbotService) {
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
}
