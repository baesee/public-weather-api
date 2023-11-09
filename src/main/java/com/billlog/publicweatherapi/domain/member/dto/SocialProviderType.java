package com.billlog.publicweatherapi.domain.member.dto;

import lombok.Getter;

@Getter
public enum SocialProviderType {
    KAKAO("K", "kakao"),
    APPLE("A", "apple")
    ;

    private final String type;
    private final String provider;

    SocialProviderType(String type, String provider) {
        this.type = type;
        this.provider = provider;
    }
}
