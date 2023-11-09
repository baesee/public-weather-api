package com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums;

import lombok.Getter;

@Getter
public enum ShortForecastCommonCode {
    /* 단기예보 공통 응답 코드값 */
    POP("강수확률", "%"),
    PTY("강수형태", "코드값"),
    PCP("1시간 강수량","1mm"),
    REH("습도","%"),
    SNO("1시간 신적설","1cm"),
    SKY("하늘상태","코드값"),
    TMP("1시간 기온","℃"),
    TMN("일 최저기온","℃"),
    TMX("일 최고기온","℃"),
    UUU("풍속(동,서 성분)","m/s"),
    VVV("풍속(남,북 성분)","m/s"),
    WAV("파고","M"),
    VEC("풍향","deg"),
    WSD("풍속","m/s"),
    T1H("기온", "℃"),
    RN1("1시간 강수량", "mm"),
    LGT("낙뢰","kA");

    private final String description;
    private final String unit;
    ShortForecastCommonCode(String description, String unit) {
        this.description = description;
        this.unit = unit;
    }

    public static String fromString(String text) {
        for (ShortForecastCommonCode code : ShortForecastCommonCode.values()) {
            if (code.name().equalsIgnoreCase(text)) {
                return code.getDescription();
            }
        }
        return null;
    }
}
