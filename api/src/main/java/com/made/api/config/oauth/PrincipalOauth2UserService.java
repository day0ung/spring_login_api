package com.made.api.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로 부터 받은 userRequest 데이터에 대한 후 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration" + userRequest.getClientRegistration()); //registrationId로 어떤 OAuth로 로그인했는지 확인가능
        System.out.println("getAccessToken" + userRequest.getAccessToken());
        /*
        1. 구글 로그인버튼 -> 구글 로그인완료
        2. code를 리턴(OAuth-Client라이브러리) ->  Access Token 요청
        3. userRequest정보 -> loadUser함수 호출 회원 구글로 부터 프로필을 받음
         */
        System.out.println("getAttributes" + super.loadUser(userRequest).getAttributes());

        //super.loadUser(userRequest).getAttributes() 정보를 토대로 강제로 회원가입
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
