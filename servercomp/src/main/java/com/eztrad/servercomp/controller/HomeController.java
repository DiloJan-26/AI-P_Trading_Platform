// step -3 - home java class

package com.eztrad.servercomp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "welcome to trading platform";
    }
}
