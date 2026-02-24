package com.eztrad.chatbotserver.controller;

// step 3 - create a controller to handle the home endpoint

import com.eztrad.chatbotserver.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse>Home() {
        ApiResponse response = new ApiResponse();
        response.setMessage("Welcome to the Chatbot Server API!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
