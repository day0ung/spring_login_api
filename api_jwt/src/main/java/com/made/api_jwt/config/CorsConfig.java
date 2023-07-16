package com.made.api_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답할 때 json을 JS에서 처리할 수 있게 설정
        config.addAllowedOrigin("*"); //all ip allow
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // post,get,put,delete,patch allow
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
