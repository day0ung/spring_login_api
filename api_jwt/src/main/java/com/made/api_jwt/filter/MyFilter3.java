package com.made.api_jwt.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter 3");
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;


        //Token : myt
        /*
        id,pw가 정상적으로 들어와서 로그인이 완료되면 토큰 생성 후 응답
        요청할 때마다 header에 Authorization에 value 값으로 토큰을 가지고옴
        토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증(RSA, HS256)
         */
        if(httpServletRequest.getMethod().equals("POST")){
            String headerAuth = httpServletRequest.getHeader("Authorization");
            System.out.println(headerAuth);

            if(headerAuth.equals("myt")){
                chain.doFilter(httpServletRequest, httpServletResponse);
            }else{
                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.println("인증 안됌");
            }
        }
    }
}
