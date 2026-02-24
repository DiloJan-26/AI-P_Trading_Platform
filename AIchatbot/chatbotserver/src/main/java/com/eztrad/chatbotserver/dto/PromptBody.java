package com.eztrad.chatbotserver.dto;

// ============================================================================
// STEP 10 - REQUEST PAYLOAD CLASS
// ============================================================================
// This DTO (Data Transfer Object) represents the request body for
// chat-related endpoints (/ai/chat, /ai/chat/simple)
//
// USAGE:
// When client sends JSON request, Spring Boot deserializes it to
// PromptBody object using Jackson JSON binding.
//
// REQUEST EXAMPLE:
// {
//   "prompt": "bitcoin"
// }
// ============================================================================

import lombok.Data;

@Data
public class PromptBody {

    // Step 10.1 - User input prompt
    // Examples:
    // - "bitcoin" (coin lookup)
    // - "ethereum" (coin lookup)
    // - "What is blockchain?" (AI chat)
    // - "Explain DeFi" (AI chat)
    // Note: For /ai/chat endpoint, this is a coin name/symbol
    //       For /ai/chat/simple endpoint, this is any text prompt
    public String prompt;
}
