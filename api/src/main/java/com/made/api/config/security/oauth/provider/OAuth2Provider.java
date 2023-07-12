package com.made.api.config.security.oauth.provider;

public interface OAuth2Provider {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
