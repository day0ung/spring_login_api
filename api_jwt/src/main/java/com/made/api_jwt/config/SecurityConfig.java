package com.made.api_jwt.config;

import com.made.api_jwt.jwt.JwtAuthenticationFilter;
import com.made.api_jwt.jwt.JwtAuthorizationFilter;
import com.made.api_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    private  final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //session X


        //요청에 대한 권한 설정
        http.authorizeRequests()
                .antMatchers("/api/v1/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        // filter
        /*
        http.addFilter(new MyFilter1());
        Error : Consider using addFilterBefore or addFilterAfter instead.
        Solve :  http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class);
        Security filterchain이 내가만든 MyFilter보다 먼저실행된다
         */

        http.addFilter(corsFilter) //인증이 필요한요청(로그인) - 시큐리티 필터에 등록해주어야함 but @CrossOrigin(인증 X)
            .addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManager
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository));


    }
}
