package com.made.api.config.security;


import com.made.api.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //IoC 빈(bean)을 등록
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // @Secured,  (@PreAuthorize, @PostAuthorize)어노테이션 활성화
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean // 해당 메서드의 리턴되는 오브젝트를 Ioc로 등록
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만되면 들어갈수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() //나머지주소는 모두 권한허용
                .and()
                .formLogin()
                .loginPage("/loginForm") //로그인을 하면 default로 보냄
                //.usernameParameter("username2") loginForm.html에서 name="username"을 변경한다면 맞춰주어야 PrincipalDetailsService
                .loginProcessingUrl("/login") // Login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행 controller에서 /login을 만들지않아도됨
                .defaultSuccessUrl("/")
                .and()
                /*
                    1. 코드받기(인증)
                    2. 엑세스토큰 받기(사용자정보에 접근할 권한)
                    3. 권할을 통해 사용자 프로필정보를 가져옴
                    4. 정보를 토대로 회원가입 자동진행
                    4-1. (이메일, 전화번호,이름,아이디) 추가 정보가 필요한 경우 추가 회원가입 프로세스
                 */
                .oauth2Login()
                .loginPage("/loginForm") //구글 로그인이 완료된 후 후처리 필요
                .userInfoEndpoint()
                .userService(principalOauth2UserService) // Tip. 구글 로그인이 완료가 되면 (AccessToken + 사용자 프로필 정보)
                ;

    }
}
