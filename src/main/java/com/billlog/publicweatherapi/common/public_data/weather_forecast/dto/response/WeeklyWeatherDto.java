package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeeklyWeatherDto {
    /* 주간(중기) 날씨 정보 DTO */
    @Schema(description = "3일후 오전 강수확률")
    private String after3DayAMRainFallProbability;
    @Schema(description = "3일후 오후 강수확률")
    private String after3DayPMRainFallProbability;
    @Schema(description = "3일 후 예상최저기온")
    private String after3DayLowestTemperature;
    @Schema(description = "3일 후 예상최고기온")
    private String after3DayHighestTemperature;
    @Schema(description = "3일후 오전 날씨예보")
    private String after3DayAMWeather;
    @Schema(description = "3일후 오후 날씨예보")
    private String after3DayPMWeather;


    @Schema(description = "4일후 오전 강수확률")
    private String after4DayAMRainFallProbability;
    @Schema(description = "4일후 오후 강수확률")
    private String after4DayPMRainFallProbability;
    @Schema(description = "4일 후 예상최저기온")
    private String after4DayLowestTemperature;
    @Schema(description = "4일 후 예상최고기온")
    private String after4DayHighestTemperature;
    @Schema(description = "4일후 오전 날씨예보")
    private String after4DayAMWeather;
    @Schema(description = "4일후 오후 날씨예보")
    private String after4DayPMWeather;

    @Schema(description = "5일후 오전 강수확률")
    private String after5DayAMRainFallProbability;
    @Schema(description = "3일후 오후 강수확률")
    private String after5DayPMRainFallProbability;
    @Schema(description = "5일 후 예상최저기온")
    private String after5DayLowestTemperature;
    @Schema(description = "5일 후 예상최고기온")
    private String after5DayHighestTemperature;
    @Schema(description = "5일후 오전 날씨예보")
    private String after5DayAMWeather;
    @Schema(description = "5일후 오후 날씨예보")
    private String after5DayPMWeather;

    @Schema(description = "6일후 오전 강수확률")
    private String after6DayAMRainFallProbability;
    @Schema(description = "6일후 오후 강수확률")
    private String after6DayPMRainFallProbability;
    @Schema(description = "6일 후 예상최저기온")
    private String after6DayLowestTemperature;
    @Schema(description = "6일 후 예상최고기온")
    private String after6DayHighestTemperature;
    @Schema(description = "6일 후 오전 날씨예보")
    private String after6DayAMWeather;
    @Schema(description = "6일후 오후 날씨예보")
    private String after6DayPMWeather;

    @Schema(description = "7일후 오전 강수확률")
    private String after7DayAMRainFallProbability;
    @Schema(description = "7일후 오후 강수확률")
    private String after7DayPMRainFallProbability;
    @Schema(description = "7일 후 예상최저기온")
    private String after7DayLowestTemperature;
    @Schema(description = "7일 후 예상최고기온")
    private String after7DayHighestTemperature;
    @Schema(description = "7일후 오전 날씨예보")
    private String after7DayAMWeather;
    @Schema(description = "7일후 오후 날씨예보")
    private String after7DayPMWeather;

    @Schema(description = "8일후 강수확률")
    private String after8DayRainFallProbability;
    @Schema(description = "8일 후 예상최저기온")
    private String after8DayLowestTemperature;
    @Schema(description = "8일 후 예상최고기온")
    private String after8DayHighestTemperature;
    @Schema(description = "8일 후 날씨예보")
    private String after8DayWeather;

    @Schema(description = "9일후 강수확률")
    private String after9DayRainFallProbability;
    @Schema(description = "9일 후 예상최저기온")
    private String after9DayLowestTemperature;
    @Schema(description = "9일 후 예상최고기온")
    private String after9DayHighestTemperature;
    @Schema(description = "9일 후 날씨예보")
    private String after9DayWeather;

    @Schema(description = "10일후 강수확률")
    private String after10DayRainFallProbability;
    @Schema(description = "10일 후 예상최저기온")
    private String after10DayLowestTemperature;
    @Schema(description = "10일 후 예상최고기온")
    private String after10DayHighestTemperature;
    @Schema(description = "10일 후 날씨예보")
    private String after10DayWeather;
    @Builder
    public WeeklyWeatherDto(String after3DayAMRainFallProbability, String after3DayPMRainFallProbability, String after3DayLowestTemperature, String after3DayHighestTemperature, String after3DayAMWeather, String after3DayPMWeather, String after4DayAMRainFallProbability, String after4DayPMRainFallProbability, String after4DayLowestTemperature, String after4DayHighestTemperature, String after4DayAMWeather, String after4DayPMWeather, String after5DayAMRainFallProbability, String after5DayPMRainFallProbability, String after5DayLowestTemperature, String after5DayHighestTemperature, String after5DayAMWeather, String after5DayPMWeather, String after6DayAMRainFallProbability, String after6DayPMRainFallProbability, String after6DayLowestTemperature, String after6DayHighestTemperature, String after6DayAMWeather, String after6DayPMWeather, String after7DayAMRainFallProbability, String after7DayPMRainFallProbability, String after7DayLowestTemperature, String after7DayHighestTemperature, String after7DayAMWeather, String after7DayPMWeather, String after8DayRainFallProbability, String after8DayLowestTemperature, String after8DayHighestTemperature, String after8DayWeather, String after9DayRainFallProbability, String after9DayLowestTemperature, String after9DayHighestTemperature, String after9DayWeather, String after10DayRainFallProbability, String after10DayLowestTemperature, String after10DayHighestTemperature, String after10DayWeather) {
        this.after3DayAMRainFallProbability = after3DayAMRainFallProbability;
        this.after3DayPMRainFallProbability = after3DayPMRainFallProbability;
        this.after3DayLowestTemperature = after3DayLowestTemperature;
        this.after3DayHighestTemperature = after3DayHighestTemperature;
        this.after3DayAMWeather = after3DayAMWeather;
        this.after3DayPMWeather = after3DayPMWeather;
        this.after4DayAMRainFallProbability = after4DayAMRainFallProbability;
        this.after4DayPMRainFallProbability = after4DayPMRainFallProbability;
        this.after4DayLowestTemperature = after4DayLowestTemperature;
        this.after4DayHighestTemperature = after4DayHighestTemperature;
        this.after4DayAMWeather = after4DayAMWeather;
        this.after4DayPMWeather = after4DayPMWeather;
        this.after5DayAMRainFallProbability = after5DayAMRainFallProbability;
        this.after5DayPMRainFallProbability = after5DayPMRainFallProbability;
        this.after5DayLowestTemperature = after5DayLowestTemperature;
        this.after5DayHighestTemperature = after5DayHighestTemperature;
        this.after5DayAMWeather = after5DayAMWeather;
        this.after5DayPMWeather = after5DayPMWeather;
        this.after6DayAMRainFallProbability = after6DayAMRainFallProbability;
        this.after6DayPMRainFallProbability = after6DayPMRainFallProbability;
        this.after6DayLowestTemperature = after6DayLowestTemperature;
        this.after6DayHighestTemperature = after6DayHighestTemperature;
        this.after6DayAMWeather = after6DayAMWeather;
        this.after6DayPMWeather = after6DayPMWeather;
        this.after7DayAMRainFallProbability = after7DayAMRainFallProbability;
        this.after7DayPMRainFallProbability = after7DayPMRainFallProbability;
        this.after7DayLowestTemperature = after7DayLowestTemperature;
        this.after7DayHighestTemperature = after7DayHighestTemperature;
        this.after7DayAMWeather = after7DayAMWeather;
        this.after7DayPMWeather = after7DayPMWeather;
        this.after8DayRainFallProbability = after8DayRainFallProbability;
        this.after8DayLowestTemperature = after8DayLowestTemperature;
        this.after8DayHighestTemperature = after8DayHighestTemperature;
        this.after8DayWeather = after8DayWeather;
        this.after9DayRainFallProbability = after9DayRainFallProbability;
        this.after9DayLowestTemperature = after9DayLowestTemperature;
        this.after9DayHighestTemperature = after9DayHighestTemperature;
        this.after9DayWeather = after9DayWeather;
        this.after10DayRainFallProbability = after10DayRainFallProbability;
        this.after10DayLowestTemperature = after10DayLowestTemperature;
        this.after10DayHighestTemperature = after10DayHighestTemperature;
        this.after10DayWeather = after10DayWeather;
    }
}
