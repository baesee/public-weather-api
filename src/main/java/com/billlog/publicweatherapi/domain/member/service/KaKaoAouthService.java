package com.billlog.publicweatherapi.domain.member.service;

import com.billlog.publicweatherapi.domain.member.dao.MemberRepository;
import com.billlog.publicweatherapi.domain.member.domain.Member;
import com.billlog.publicweatherapi.domain.member.dto.SocialProviderType;
import com.billlog.publicweatherapi.domain.member.dto.request.MemberDto;
import com.billlog.publicweatherapi.domain.member.dto.response.SoicalInfoDto;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class KaKaoAouthService {
    @Value("${security.oauth2.client.registration.kakao.clientId}")
    String clientId;

    @Value("${security.oauth2.client.registration.kakao.clientSecret}")
    String clientSecret;

    @Value("${security.oauth2.client.registration.kakao.redirectUri}")
    String redirectUri;

    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final MemberLoginService memberLoginService;

    /**
     * 테스트 코드 : 카카오 로그인 페이지가 없기때문에 getKakaoAccessToken을 호출 하여 코드값으로 kakao accesstoekrn값을 받아온다.
     * @param code
     * @return
     */
    public String getKakaoAccessToken(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        SoicalInfoDto.kakaoToeknInfoRequest tokenInfo = webClient.mutate()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("Internal Server Error")))
                .bodyToMono(SoicalInfoDto.kakaoToeknInfoRequest.class)
                .block();

        return tokenInfo.getAccessToken();

    }

    public SoicalInfoDto.kakaoUserInfoRequest getKakaoUserInfo(String accessToken){
        return webClient.mutate().build().get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("Internal Server Error")))
                .bodyToMono(SoicalInfoDto.kakaoUserInfoRequest.class)
                .block();
    }

    /**
     * 소셜 사용자(Kakao) 엑세스토큰을 받아 기회원일 경우 로그인을, 신규회원일 경우 회원가입 유도
     * @param accessToken
     * @return
     */
    public Object getKakaoSoicalUserProcessByAccessToken(String accessToken){
        // 1. 카카오 AccessToken을 이용하여 사용자 정보 조회
        SoicalInfoDto.kakaoUserInfoRequest kakaoUserInfo = this.getKakaoUserInfo(accessToken);

        // 2. 가입된 소셜 사용자라면 로그인처리
        Optional<Member> member = memberRepository.findByMemberId(kakaoUserInfo.getId());
        if (!member.isEmpty()) {
            MemberDto.loginRequest loginRequest = MemberDto.loginRequest.builder()
                    .memberId(member.get().getMemberId())
                    .password(" ") // 소셜 사용자의 경우 패스워드가 공백.
                    .build();

            return memberLoginService.socialLogin(loginRequest);
        }

        return SoicalInfoDto.socialSignupInfoResponse.builder()
                .socialId(kakaoUserInfo.getId())
                //.email(kakaoUserInfo.getKakaoAccount().getEmail())
                .accountType(SocialProviderType.KAKAO.getType())
                .build();
    }

}
