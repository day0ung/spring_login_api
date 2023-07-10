package com.made.api.config.auth;


import com.made.api.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

/*
1. security가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다. ( SecurityConfiguration. loginProcessingUrl("login") )
2. 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (Security ContextHolder에 세션정보 저장)
3. Security Session에는 오브젝트가 정해져있다. ( Authentication Type 객체 )
4. Authentication 안에는 User의 정보가 있어야한다.
5. User 오브젝트 타입은 UserDetails 타입 객체

Security Session -> Authentication -> UserDetails (PrincipalDetails)
 */
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user){
        this.user = user;
    }

    /*
    해당 유저의 권한을 return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        user.getRole(); 을 하여 권한을 가져와야하는데 return Type이 Collection<GrantedAuthority>이다.
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRole());
        /*
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        위 방법도되지만 람다식으로 적용해서 변경
         */
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        /*
        1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로함
        현재시간 - 로그인시간 :  1년을 초과하면 return false
        */
        return true;
    }
}
