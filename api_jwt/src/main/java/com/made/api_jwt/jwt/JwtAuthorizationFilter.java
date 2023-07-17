package com.made.api_jwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.made.api_jwt.auth.PrincipalDetails;
import com.made.api_jwt.model.User;
import com.made.api_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
1. Spring Security Filter 중 BasicAuthenticationFilter
2. 권한이나 인증이 필요한 특정 주서를 요청했을때 BasicAuthenticationFilter.doFilterInternal()를 타게된다.
2-1. 만약 권한 or 인증이 필요한 주소가 아니면 이필터를 타지않는다.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // super.doFilterInternal(request, response, chain); 지우지않으면  chain.doFilter(request,response);가 안됌
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("인증이나 권한이 필요한 주소요청됨" + jwtHeader);

        // JWT Token 검증을 해서 정상적인 사용지인지 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = jwtHeader.replace("Bearer ", "");

        String username = JWT.require(Algorithm.HMAC512("cos")).build()
                .verify(jwtToken).getClaim("usernmae").asString();

        //서명(verify)이 정상적
        if(username != null){
            User user = userRepository.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);

            //Jwt 토큰 서명을 통해서 서명이 정상이면  Authentication객체 생성
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);
        }


    }
}
