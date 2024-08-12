package com.cos.security1.config.oauth.provider;


import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        System.out.println("GoogleUserInfo.getProviderId");
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        System.out.println("GoogleUserInfo.getProvider");
        return "google";
    }
    @Override
    public String getEmail() {
        System.out.println("GoogleUserInfo.getEmail");
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        System.out.println("GoogleUserInfo.getName");
        return (String) attributes.get("name");
    }
}
