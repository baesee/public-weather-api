package com.billlog.publicweatherapi.global.common.reponse;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * BL-EM-XXX
     * BL : Billlog
     * EM : Error Manage
     * XXX : E : 기본에러
     *       CA : 인증 (Common Auth)
     *       BD : 바인딩에러 (Biding)
     *       CR : 무결성에러 (Constraint)
     *
     */
    FAIL("FAIL","BL-EM-E000", "실패하였습니다."),
    BINDING_FAIL("FAIL","BL-EM-BE000", "필수입력 항목이 누락되었습니다."),
    UNAUTHORIZED_FAIL("FAIL", "BL-EM-CA000", "인증이 필요합니다."),
    FORBIDDEN_FAIL("FAIL", "BL-EM-CA001", "허가되지 않은 접급입니다."),
    INTEGRITY_CONSTRAINT_FAIL("FAIL", "BL-EM-CR000", "무결성 제약조건에 위배되었습니다."),
    LOGIN_FAIL("FAIL","BL-EM-CA002", "아이디 또는 패스워드를 확인해주세요."),
    ;

    private final String status;
    private final String code;
    private final String message;
    ErrorCode(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
