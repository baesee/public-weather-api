package com.billlog.publicweatherapi.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {
    @Getter
    @Setter
    @Schema(description = "소셜 회원가입 DTO")
    public static class JoinRequest {
        @Schema(description = "소셜 고유ID")
        @NotBlank(message = "사용자ID(socialId)는 필수사항입니다.")
        private String socialId;
        @Schema(description = "소셜 타입 ( 카카오톡 : 'K')")
        @NotBlank(message = "소셜타입은 필수사항입니다.")
        private String accountType;
        @Schema(hidden = true)
        private String password;
        @Schema(description = "이용약관 여부")
        @NotBlank(message = "이용약관 동의 여부는 필수사항입니다.")
        private String policyAgree;
        @Schema(description = "성별 ( 'F','M' )")
        private String gender;
    }

    @Getter
    @Schema(description = "로그인 DTO")
    public static class loginRequest {
        @Schema(description = "사용자 ID")
        @NotBlank(message = "사용자ID는 필수사항입니다.")
        @Email
        private String memberId;

        @Schema(description = "패스워드", example = "1234")
        @NotBlank(message = "패스워드는 필수사항입니다.")
        private String password;

        @Builder
        public loginRequest(String memberId, String password) {
            this.memberId = memberId;
            this.password = password;
        }
    }

    @Getter
    @Schema(description = "토큰 재발행 DTO")
    public static class reissueTokenRequest {
        @Schema(description = "엑세스 토큰(Access Token)")
        @NotBlank(message = "액세스 토큰은 필수사항입니다.")
        private String accessToken;
        @Schema(description = "리프레시 토큰(Refresh Token)")
        @NotBlank(message = "리프레시 토큰은 필수사항입니다.")
        private String refreshToken;
    }
}
