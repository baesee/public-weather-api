package com.billlog.publicweatherapi.global.jwt;

import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 헤더에서 JWT 를 받아옵니다.
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

		// Request한 URI 조회
		String requestURI = ((HttpServletRequest) request).getRequestURI();

		// 유효한 토큰인지 확인합니다.
		if (token != null && jwtTokenProvider.validateToken(token) || requestURI.contains("/reissue/token") && token != null ) {
			// requestURI.contains("/reissue/token") : 토큰재발행
			// 토큰 재발행시에는 Accesstoken가 이미 만료된 상황 일 수도 있기떄문에 토큰 유효성 체크에서 걸리지 않도록 예외처리
			try {
				// 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				// SecurityContext 에 Authentication 객체를 저장합니다.
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (BLBusinessException e) {
				log.error("Jwt Filter 토큰 유효성 체크 중 에러 발생 : " + e);
			}
		}
		chain.doFilter(request, response);
	}

}
