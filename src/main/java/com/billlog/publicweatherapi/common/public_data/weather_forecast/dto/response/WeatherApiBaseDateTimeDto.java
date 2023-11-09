package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WeatherApiBaseDateTimeDto {
    /* 기상청 API 호출에 필요한발표일자, 발표일시, 발표시각을 담을 그릇 */
    public String baseDate; // 단기예보 발표일자,
    public String baseTime; // 단기예보 발표시각,
    public String tmFc; // 중기예보 조회서비스에 사용되는 발표시각 필드 (tmFc , 일 2회 06:00, 18:00)
    @Builder
    public WeatherApiBaseDateTimeDto(String baseDate, String baseTime, String tmFc) {
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.tmFc = tmFc;
    }
}
