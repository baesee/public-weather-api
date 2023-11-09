package com.billlog.publicweatherapi.global.custom.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import com.billlog.publicweatherapi.global.common.reponse.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	/* UNAUTHORIZED (401 - 인증필요) 에러 재정의 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(401);

		try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ApiResponseDTO.FAIL(ErrorCode.UNAUTHORIZED_FAIL));
            os.flush();
        }

	}

}
