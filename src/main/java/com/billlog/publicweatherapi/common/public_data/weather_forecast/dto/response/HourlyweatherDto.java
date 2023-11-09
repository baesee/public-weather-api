package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyweatherDto {
    /* 시간별 날씨 정보 DTO */
    @Schema(description = "기온")
    private String temperature;
    @Schema(description = "하늘상태(맑음, 구름많음, 흐림)")
    private String skyState;
    @Schema(description = "습도 (%)")
    private String humidity;
    @Schema(description = "강수확률 (%)")
    private String rainFallProbability;
    @Schema(description = "1시간 강수량(mm)")
    private String rainfall;
    @Schema(description = "풍향")
    private String windDirection;
    @Schema(description = "풍속(m/s)값")
    private String windSpeedValue;
    @Schema(description = "예보일")
    private String forecastDate;
    @Schema(description = "예보시각")
    private String forecastTime;
    @Builder
    public HourlyweatherDto(String temperature, String skyState, String humidity, String rainFallProbability, String rainfall, String windDirection, String windSpeedValue, String forecastDate, String forecastTime) {
        this.temperature = temperature;
        this.skyState = skyState;
        this.humidity = humidity;
        this.rainFallProbability = rainFallProbability;
        this.rainfall = rainfall;
        this.windDirection = windDirection;
        this.windSpeedValue = windSpeedValue;
        this.forecastDate = forecastDate;
        this.forecastTime = forecastTime;
    }
}
