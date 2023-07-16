package com.made.api_jwt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.made.api_jwt.auth.PrincipalDetails;
import com.made.api_jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Spring Security 에서 UsernamePasswordAuthenticationFilter 흐름
1. "/login" 요청시 POST {username, password}
2. UsernamePasswordAuthenticationFilter 동작
3. But SecurityConfig에서 formLogin.disable() 했기때문에 작동 X
4. 로그인하기 위해 SecurityConfig에 addFilter(JwtAuthenticationFilter)로 등록
5. UsernamePasswordAuthenticationFilter는 AuthenticationManager를 통해서 로그인을 진행하기 때문에 파라미터로 필요하다
6. AuthenticationManager 로그인시도  -> attemptAuthentication()실행
    6-1. username,passwrd 받아서 정상인지 확인
7. PricipalDetailService 호출 loadByUsername() -> return Autentication(Security Session 내부)
    7-1. 정상이면 DB의 username, password가 일치한다.
8. PrincipalDetilas를 세션에 담고(권한관리를 위해) JWT토큰을 만들어 응답
    8-1. attemptAuthentication() 정상적으로 실행후 -> successfulAuthentication()
    8-2. JWT 토큰을 만들어 request요청한 사용자에게 JWT토큰을 response 해주면됨
 */

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Login attempt");
        User user = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper JSON과 Java 객체 간의 매핑
            user = objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        //7번 실행 authentication 객체가 session영역에 저장됨 <- 로그인한 정보가담겨있다.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Login Success" + principalDetails.getUser().getUsername());

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication : 인증이 완료되었다는뜻");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
