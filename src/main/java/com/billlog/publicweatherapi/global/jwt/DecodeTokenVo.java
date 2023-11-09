package com.billlog.publicweatherapi.global.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecodeTokenVo {
	private String sub;
	private Info info;
	private String roles;

	@Getter
	@Setter
	public static class Info{
		private String memberId;
		private String name;
		private String account;
		private String symbol;
		private String email;

    }
	
}
