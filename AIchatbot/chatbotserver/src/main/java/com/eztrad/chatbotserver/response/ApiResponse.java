package com.eztrad.chatbotserver.response;

// ============================================================================
// STEP 4 - API RESPONSE CLASS
// ============================================================================
// This class is a generic response wrapper for all API endpoints.
// It standardizes the response format across all endpoints.
//
// USAGE:
// All endpoints return ApiResponse with:
// - message: Human-readable status/description
// - data: Payload (Coin object, null, or any other data)
// ============================================================================

import lombok.Data;

@Data
public class ApiResponse {

    // Step 4.1 - Status message for the response
    // Examples: "Welcome to the Chatbot Server API!"
    //           "Coin data fetched successfully"
    //           "Error message here"
    private String message;

    // Step 4.1 - Optional response payload for API data
    // Can hold:
    // - Coin object (for /ai/chat endpoint)
    // - Gemini response JSON (for /ai/chat/simple endpoint)
    // - null (for simple status responses)
    private Object data;
}
