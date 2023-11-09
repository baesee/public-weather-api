package com.billlog.publicweatherapi.global.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class JwtTokenInfoVo {
	@Schema(description = "로그인 AccessToken")
	private String accessToken;
	@Schema(description = "로그인 RefreshToken")
	private String refreshToken;

}
