package com.eztrad.chatbotserver.service;

// Step 5 - create a service interface to define the chatbot service methods

import com.eztrad.chatbotserver.response.ApiResponse;

public interface ChatbotService {

    ApiResponse getCoinDetails(String prompt) throws Exception;

    String simpleChat(String prompt);
}
