package com.made.api.controller;

import com.made.api.domain.User;
import com.made.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(){
        return "index";
    }

//    @GetMapping("/login")
//    //초기에 login페이지를 security가 낚아챔 -> SecurityConfiguration 설정후엔 사라짐
//    public String Login(){
//        return "loginForm";
//    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
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

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        //userRepository.save(user); // -> 시큐리티로 로그인을 할수 없음 패스워드 암호화가 안돼어서
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 함수가 시작되기전 실행
//    @PostAuthorize() 자주안쓴다.
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }






}
