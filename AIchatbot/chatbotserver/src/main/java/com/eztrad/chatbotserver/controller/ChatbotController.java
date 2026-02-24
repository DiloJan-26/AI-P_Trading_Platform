package com.eztrad.chatbotserver.controller;

// step 9 - create a controller to handle the chatbot endpoint and call the service to get the coin details

import com.eztrad.chatbotserver.dto.PromptBody;
import com.eztrad.chatbotserver.response.ApiResponse;
import com.eztrad.chatbotserver.service.ChatbotService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<ApiResponse> getCoinDetails(@RequestBody PromptBody prompt) throws Exception {



        chatbotService.getCoinDetails(prompt.getPrompt());

        ApiResponse response = new ApiResponse();
        response.setMessage(prompt.getPrompt());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Step 12  -
    @PostMapping("/simple")
    public ResponseEntity<String> simpleChatHandler(@RequestBody PromptBody prompt) throws Exception {

        String response = chatbotService.simpleChat(prompt.getPrompt());

//        ApiResponse response = new ApiResponse();
//        response.setMessage(prompt.getPrompt());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
