package com.billlog.publicweatherapi.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SoicalInfoDto {
    /* 소셜회원의 정보를 담기 위한 DTO */

    @Getter
    @Setter
    @Schema(description = "카카오 인증 토큰 정보 DTO")
    public static class kakaoToeknInfoRequest{
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

    }

    @Getter
    @Setter
    @Schema(description = "카카오 사용자 정보 DTO")
    public static class kakaoUserInfoRequest{
        private String id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Getter
        @Setter
        public static class KakaoAccount {
            private String email;
        }
    }

    @Getter
    @Schema(description = "소셜 회원가입시 필요한 소셜정보 DTO")
    public static class socialSignupInfoResponse{
        private String socialId;
        private String email;
        private String accountType;

        @Builder
        public socialSignupInfoResponse(String socialId, String email, String accountType) {
            this.socialId = socialId;
            this.email = email;
            this.accountType = accountType;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "애플로부터 전달 받은 인증 토큰 정보 DTO")
    public static class appleToeknInfo{
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("id_token")
        private String idToken;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "애플 사용자 정보 DTO")
    public static class appleIdToeknDecodeInfo{

        @JsonProperty("sub")
        private String id;
        @JsonProperty("email")
        private String email;

        @JsonProperty("email_verified")
        private String emailVerified;

        @JsonProperty("is_private_email")
        private String isPrivateEmail;
    }
}
