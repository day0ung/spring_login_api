package com.made.api.config.security.oauth;

import com.made.api.config.security.auth.PrincipalDetails;
import com.made.api.config.security.oauth.provider.*;
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

import java.util.Map;


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

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); //OAuth2 로그인 시 키(PK)가 되는 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",super.loadUser(userRequest).getAttributes() );
        /*
        1. 구글 로그인버튼 -> 구글 로그인완료
        2. code를 리턴(OAuth-Client라이브러리) ->  Access Token 요청
        3. userRequest정보 -> loadUser함수 호출 회원 구글로 부터 프로필을 받음
         */

        //super.loadUser(userRequest).getAttributes() 정보를 토대로 강제로 회원가입
        /*
        Google과 FaceBook의 attribute가 다르기 때문에 Interface생성해 분기처리
         */
        OAuth2Provider oAuth2Provider = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2Provider =  new GoogleProvider(oAuth2User.getAttributes());
            log.info("google");
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2Provider =  new FaceBookProvider(oAuth2User.getAttributes());
            log.info("faceBook");
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2Provider =  new NaverProvider((Map)oAuth2User.getAttributes().get("response"));
            log.info("naver");
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2Provider =  new KakaoProvider(oAuth2User.getAttributes());
            log.info("kakao");
        } else{
            log.info("구글과 페이스북만 지원해요");
        }


        String provider = oAuth2Provider.getProvider();
        String providerId = oAuth2Provider.getProviderId();// oAuth2User.getAttribute("sub"); //facebook = "id",
        String username = String.format("%s_%s", provider, providerId);
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2Provider.getEmail();
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
