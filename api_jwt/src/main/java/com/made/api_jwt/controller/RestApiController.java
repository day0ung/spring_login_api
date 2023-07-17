package com.made.api_jwt.controller;

import com.made.api_jwt.model.User;
import com.made.api_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("home")
    public String home(){
        return "<h1>HOME</h1>";
    }

    @PostMapping("token")
    public String token(){
        return "<h1>Token</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        userRepository.save(user);
        return "<h1>Join</h1>";

        /*
        Headers Content-Type = application/json
         */
    }

    @GetMapping("/api/v1/user")
    public String user(){
        return "user";
    }

    @GetMapping("/api/v1/admin")
    public String admin(){
        return "admin";
    }






}
