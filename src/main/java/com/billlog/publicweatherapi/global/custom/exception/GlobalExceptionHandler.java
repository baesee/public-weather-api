package com.billlog.publicweatherapi.global.custom.exception;

import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import com.billlog.publicweatherapi.global.common.reponse.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid이용시 Binding에러 발생시 메시지 전달
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleValidationExceptions(BindingResult e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.BINDING_FAIL(e));
    }

    // 로그인(인증) 관련 에러
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(ErrorCode.FAIL));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.FAIL(ErrorCode.LOGIN_FAIL));
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(ErrorCode.FAIL));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(ErrorCode.INTEGRITY_CONSTRAINT_FAIL));
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(ErrorCode.INTEGRITY_CONSTRAINT_FAIL));
    }
    // 로그인(인증) 관련 에러 End.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleDataIntegrityViolationException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
    @ExceptionHandler(BLBusinessException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleBLBusinessException(BLBusinessException e) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.FAIL(e.getMessage()));
    }
}
