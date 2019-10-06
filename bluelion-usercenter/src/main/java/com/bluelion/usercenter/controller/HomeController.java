package com.bluelion.usercenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/health")
    public String health(){
        return "health";
    }

    @GetMapping("/greeting")
    public String greeting(){
        return "Welcome to Spring cloud";
    }
}
