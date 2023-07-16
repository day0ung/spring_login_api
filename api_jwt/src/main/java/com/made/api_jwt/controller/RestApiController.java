package com.made.api_jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @GetMapping("home")
    public String home(){
        return "<h1>HOME</h1>";
    }

    /*
    "error": "Method Not Allowed",
    "path": "/home"
     */
    @PostMapping("token")
    public String token(){
        return "<h1>Token</h1>";
    }


}
