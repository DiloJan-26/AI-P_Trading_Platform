// step -3 - home java class
// Create the initial HomeController with a public endpoint.
// This serves as the entry point for testing the application without authentication.

package com.eztrad.servercomp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // public endpoint
    @GetMapping
    public String home(){
        return "welcome to trading platform";
    }

    // Step 20 - secured endpoint - it won't work it protected by jwt token validator filter
    @GetMapping("/api")
    public String secured(){
        return "welcome to trading platform secured";
    }
}
