package com.billlog.publicweatherapi.global.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class TokenPayloadDto {
	private String memberNo;		// userNo
	private String memberId;		// 유저ID
	@Builder
	public TokenPayloadDto(String memberNo, String memberId) {
		this.memberNo = memberNo;
		this.memberId = memberId;
	}
}
