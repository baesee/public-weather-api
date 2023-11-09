package com.billlog.publicweatherapi.global.common.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ApiResponseDTO<T>{
    @Schema(description = "에러코드")
    private String code;
    @Schema(description = "응답 상태")
    private String status;
    @Schema(description = "결과 데이터")
    private T data;
    @Schema(description = "응답 메시지")
    private String message;

    public static ApiResponseDTO<?> SUCCESS() {
        return new ApiResponseDTO<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getStatus(), null, null);
    }

    public static <T> ApiResponseDTO<T> SUCCESS(T data) {
        return new ApiResponseDTO<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getStatus(), data, null);
    }

    public static <T> ApiResponseDTO<T> SUCCESS(T data, String message) {
        return new ApiResponseDTO<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getStatus(), data, message);
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static ApiResponseDTO<?> BINDING_FAIL(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponseDTO<>(ErrorCode.BINDING_FAIL.getCode(), ErrorCode.BINDING_FAIL.getStatus(), errors, ErrorCode.BINDING_FAIL.getMessage());
    }


    public static ApiResponseDTO<?> FAIL(String msg) {
        return new ApiResponseDTO<>(ErrorCode.FAIL.getCode(), ErrorCode.FAIL.getStatus(), null, msg);
    }

    public static ApiResponseDTO<?> FAIL(ErrorCode errorCode) {
        return new ApiResponseDTO<>(errorCode.getCode(), errorCode.getStatus(), null, errorCode.getMessage());
    }

    public ApiResponseDTO(String code, String status, T data, String message) {
        this.code = code;
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
