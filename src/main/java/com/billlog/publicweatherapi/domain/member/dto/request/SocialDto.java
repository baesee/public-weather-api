package com.billlog.publicweatherapi.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class SocialDto {
    @Getter
    @Schema(description = "소셜 엑세스 토큰 DTO")
    public static class accessTokenRequest {
        @Schema(description = "소셜 제공자에게서 전달받은 카카오(AccessToken) or 애플(IdToken)")
        @NotBlank(message = "AccessToken값은 필수사항입니다.")
        private String accessToken;
    }
}
