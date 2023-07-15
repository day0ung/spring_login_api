# spring_login_api

### Spring Security

```
Spring Security 안에는

Security Session {
    Authentication{ UserDetails, Oauth2User }

}

UserDetails: 일반로그인
Oauth2User : 소셜로그인

```
Authentication안에는 UserDetails나 Oauth2User로만 받아야한다
Controller에서 어떤것을 인자로 받을지 관리하기가 어렵기 때문에, 

A라는 클래스에 implements UserDetails, Oauth2User 두개다 상속받아 사용하도록 한다. 

