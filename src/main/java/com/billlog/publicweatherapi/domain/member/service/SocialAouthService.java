package com.billlog.publicweatherapi.domain.member.service;

import com.billlog.publicweatherapi.domain.member.dto.SocialProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SocialAouthService {
    private final KaKaoAouthService kaKaoAouthService;
    /**
     * @param provider
     * @param accessToken
     * @return
     * @throws IOException
     */
    public Object getSocialAuthProcessByAccessToken(String provider, String accessToken) throws IOException {
        if (provider.equals(SocialProviderType.KAKAO.getProvider())) {
            return kaKaoAouthService.getKakaoSoicalUserProcessByAccessToken(accessToken);
        }
        throw new IllegalArgumentException("존재하지 않는 제공자입니다.");
    }
}
