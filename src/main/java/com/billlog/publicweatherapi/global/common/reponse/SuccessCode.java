package com.billlog.publicweatherapi.global.common.reponse;

import lombok.Getter;

@Getter
public enum SuccessCode {
    /**
     * BL--SM-XXX
     * BL : Billlog
     * SM : Success Manage
     * XXX : S : 기본성공
     *
     */
    SUCCESS("SUCCESS", "BL-SM-S000")
    ;
    private final String status;
    private final String code;

    SuccessCode(String status, String code) {
        this.status = status;
        this.code = code;
    }
}
