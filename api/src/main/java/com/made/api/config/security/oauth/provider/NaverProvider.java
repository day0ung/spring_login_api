package com.made.api.config.security.oauth.provider;

import java.util.Map;

public class NaverProvider implements OAuth2Provider {

    private Map<String, Object> attributes; //Oauth2User.attributes

    public NaverProvider(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
