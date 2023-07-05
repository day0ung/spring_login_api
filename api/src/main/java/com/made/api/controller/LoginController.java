package com.made.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    //초기에 login페이지를 security가 낚아챔 -> SecurityConfiguration 설정후엔 사라짐
    public String Login(){
        return "login";
    }

    @RequestMapping("/user")
    public String User(){
        return "user";
    }

    @RequestMapping("/amdin")
    public String Admin(){
        return "amdin";
    }

    @RequestMapping("/manager")
    public String Manager(){
        return "manager";
    }





}
