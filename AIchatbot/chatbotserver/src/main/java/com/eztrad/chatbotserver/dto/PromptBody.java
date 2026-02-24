package com.eztrad.chatbotserver.dto;

// Step 10 - create a DTO class to represent the request body for the chatbot endpoint

import lombok.Data;

@Data
public class PromptBody {

    public String prompt;
}
