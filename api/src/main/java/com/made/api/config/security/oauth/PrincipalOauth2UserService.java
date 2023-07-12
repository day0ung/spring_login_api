package com.made.api.config.security.oauth;

import com.made.api.config.security.auth.PrincipalDetails;
import com.made.api.domain.User;
import com.made.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration" + userRequest.getClientRegistration()); //registrationId로 어떤 OAuth로 로그인했는지 확인가능
        System.out.println("getAccessToken" + userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",super.loadUser(userRequest).getAttributes() );
        /*
        1. 구글 로그인버튼 -> 구글 로그인완료
        2. code를 리턴(OAuth-Client라이브러리) ->  Access Token 요청
        3. userRequest정보 -> loadUser함수 호출 회원 구글로 부터 프로필을 받음
         */

        //super.loadUser(userRequest).getAttributes() 정보를 토대로 강제로 회원가입
        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String username = String.format("%s_%s", provider, providerId);
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2User.getAttribute("email");
        String role= "ROLE_USER";

        User user  = userRepository.findByUsername(username);

        if (user == null){
            log.info("first Login");
            user = User.builder().username(username).password(password).email(email).role(role)
                    .provider(provider).providerId(providerId).build();

            userRepository.save(user);
        } else{
            log.info("already Login");
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes()); //return  -> Authentication
    }
}
