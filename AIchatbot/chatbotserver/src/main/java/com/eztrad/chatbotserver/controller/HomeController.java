package com.eztrad.chatbotserver.controller;

// ============================================================================
// STEP 3 - HOME CONTROLLER
// ============================================================================
// This controller handles the root endpoint for health checking.
// It verifies that the Spring Boot application is running and responsive.
//
// EXECUTION FLOW:
// Client GET / → HomeController.Home() → ApiResponse → Client
// ============================================================================

import com.eztrad.chatbotserver.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // ========================================================================
    // STEP 3.1 - ENDPOINT: Home Health Check
    // ========================================================================
    // URL: GET http://localhost:5454/
    // Purpose: Verify application is running
    // Response Status: 200 OK
    // Response Body: JSON with welcome message
    //
    // USAGE:
    // curl http://localhost:5454/
    //
    // RESPONSE:
    // {
    //   "message": "Welcome to the Chatbot Server API!",
    //   "data": null
    // }
    // ========================================================================
    @GetMapping("/")
    public ResponseEntity<ApiResponse>Home() {
        ApiResponse response = new ApiResponse();
        response.setMessage("Welcome to the Chatbot Server API!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
