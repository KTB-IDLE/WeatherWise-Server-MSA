package com.idle.authservice.infrastructure.info;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getNickName() {
        Map<String, Object> subAttributes = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> otherAttributes = (Map<String, Object>) subAttributes.get("profile");
        return otherAttributes.get("nickname").toString();
    }
}

