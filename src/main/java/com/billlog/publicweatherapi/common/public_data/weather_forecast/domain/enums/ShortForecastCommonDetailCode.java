package com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums;

import lombok.Getter;

@Getter
public enum ShortForecastCommonDetailCode {
    /*
     * 단기예보 중 특정 요소의 코드값
     * 특정 요소 :
     *  - 하늘상태 'SKY' 코드
     *  - 강수형태 'PTY' 코드
     *      1. 초단기 접두어 'U_'
     *      2. 단기
     */

    SKY_1("맑음"),
    SKY_3("구름많음"),
    SKY_4("흐림"),
    U_PTY_0("없음"),
    U_PTY_1("비"),
    U_PTY_2("비/눈"),
    U_PTY_3("눈"),
    U_PTY_5("빗방울"),
    U_PTY_6("빗방울눈날림"),
    U_PTY_7("눈날림"),
    PTY_0("없음"),
    PTY_1("비/눈"),
    PTY_3("눈"),
    PTY_4("소나기"),
    VEC_0("N"),
    VEC_1("NNE"),
    VEC_2("NE"),
    VEC_3("ENE"),
    VEC_4("E"),
    VEC_5("ESE"),
    VEC_6("SE"),
    VEC_7("SSE"),
    VEC_8("S"),
    VEC_9("SSW"),
    VEC_10("SW"),
    VEC_11("WSW"),
    VEC_12("W"),
    VEC_13("WNW"),
    VEC_14("NW"),
    VEC_15("NNW"),
    VEC_16("N");

    private final String description;
    ShortForecastCommonDetailCode(String description) {
        this.description = description;
    }

    public static String fromString(String text) {
        for (ShortForecastCommonDetailCode code : ShortForecastCommonDetailCode.values()) {
            if (code.name().equalsIgnoreCase(text)) {
                return code.getDescription();
            }
        }
        return null;
    }
}
