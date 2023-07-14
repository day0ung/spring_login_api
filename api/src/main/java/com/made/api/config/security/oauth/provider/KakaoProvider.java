package com.made.api.config.security.oauth.provider;

import java.util.Map;

public class KakaoProvider implements OAuth2Provider {

    private Map<String, Object> attributes;
    private Map<String ,Object> account;

    public KakaoProvider(Map<String, Object> attributes){
        this.attributes = attributes;
        this.account = (Map)attributes.get("kakao_account");
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String)account.get("email");
    }

    @Override
    public String getName() {
        Map<String ,Object> profile =  (Map)account.get("profile");
        return (String)profile.get("nickname");
    }
}
