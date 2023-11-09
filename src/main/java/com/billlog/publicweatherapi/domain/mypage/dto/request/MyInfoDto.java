package com.billlog.publicweatherapi.domain.mypage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MyInfoDto {
    @Getter
    @Setter
    @Schema(description = "패스워드 변경 DTO")
    public static class passwordChangeRequest {
        @Schema(description = "기존 패스워드")
        @NotBlank(message = "기존 패스워드는 필수사항입니다.")
        private String password;

        @Schema(description = "변경 패스워드")
        @NotBlank(message = "변경 패스워드는 필수사항입니다.")
        private String newPassword;
    }
    @Getter
    @Setter
    @Schema(description = "닉네임 변경 DTO")
    public static class nicknameChangeRequest {
        @Schema(description = "변경할 닉네임")
        @NotBlank(message = "변경할 닉네임은 필수사항입니다.")
        private String nickName;
    }
}
