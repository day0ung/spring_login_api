package com.made.api.config.security.auth;

import com.made.api.domain.User;
import com.made.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
Security 설정에서 loginProcessingUrl("login")
/login 요청이 오면 자동으로 UserDetailsService타입으로 Ioc 되어있는 loadUserByUsername 함수 실행

 */
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    /*
     return 되는 곳
     Security Session 내부 (Authentication(내부 userDetails))
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  =  userRepository.findByUsername(username);
        System.out.println(username);
        if(user != null){
            return new PrincipalDetails(user);
        }
        return null;
    }
}
