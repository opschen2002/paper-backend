package com.physical.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "Application is running";
    }
    
    @GetMapping("/api/test")
    public String apiTest() {
        return "API endpoint is working";
    }
}