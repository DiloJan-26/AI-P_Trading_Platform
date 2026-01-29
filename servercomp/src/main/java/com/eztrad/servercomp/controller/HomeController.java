// step -3 - home java class

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

    // Step 20 - secured endpoint
    @GetMapping("/api")
    public String secured(){
        return "welcome to trading platform secured";
    }
}
