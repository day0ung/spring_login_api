package com.made.api_jwt.auth;

import com.made.api_jwt.model.User;
import com.made.api_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
http://localhost:8080/login -> 동작을안한다.
JwtAuthenticationFilter로 동작을 하게끔해줌
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    /*@RequiredArgsConstructor ->  @Autowried 대신 final 생성자로 DI주입을 위해 사용 */
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        User user = userRepository.findByUsername(username);
        return new PrincipalDetails(user);
    }
}
