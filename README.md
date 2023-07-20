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





### JWT의 동작 과정
![](process.png)
1. 인증: 사용자가 로그인 또는 인증을 요청한다.
2. 서버 인증: 서버는 사용자의 인증 정보를 확인하고, 유효한 사용자임을 검증한다.
3. 토큰 생성: 서버는 유효한 사용자에게 JWT를 생성한다. JWT는 헤더, 페이로드, 서명 세 부분으로 구성된다.
4. 헤더(Header): 토큰의 유형 및 암호화 알고리즘 정보를 포함한다.
5. 페이로드(Payload): 클레임(Claims) 정보를 포함, 클레임은 사용자에 대한 추가 정보를 담고 있다.
6. 서명(Signature): 토큰의 무결성을 보장하기 위한 서명이다. 서버에서 비밀 키를 사용하여 생성된다.
7. 토큰 발급: 서버는 생성된 JWT를 사용자에게 발급한다.
8. 토큰 전송: 사용자는 인증된 요청을 보낼 때 JWT를 헤더나 요청 매개변수에 포함하여 서버에 전송한다.
9. 서버 인증 및 인가: 서버는 수신된 JWT를 검증하고, 클라이언트의 요청을 인증하고 권한을 확인한다. JWT의 유효성 및 서명을 확인하여 사용자를 인증하고, 요청에 대한 권한을 부여한다.
