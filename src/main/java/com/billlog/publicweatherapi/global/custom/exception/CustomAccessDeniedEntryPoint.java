package com.billlog.publicweatherapi.global.custom.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import com.billlog.publicweatherapi.global.common.reponse.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class CustomAccessDeniedEntryPoint implements AccessDeniedHandler {
	/* Forbidden (403-권한불충분) 에러 재정의 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(403);

		try (OutputStream os = response.getOutputStream()) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(os, ApiResponseDTO.FAIL(ErrorCode.FORBIDDEN_FAIL));
			os.flush();
		}
	}

}
