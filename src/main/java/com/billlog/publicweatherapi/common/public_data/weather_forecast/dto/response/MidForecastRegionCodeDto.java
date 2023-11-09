package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import lombok.Getter;

@Getter
public class MidForecastRegionCodeDto {
    private String landForecastRegionConde;         // 서중기육상예보 구역코드
    private String temperatureForecastRegionConde;  // 중기기온예보 구역코드

    public MidForecastRegionCodeDto(String landForecastRegionConde, String temperatureForecastRegionConde) {
        this.landForecastRegionConde = landForecastRegionConde;
        this.temperatureForecastRegionConde = temperatureForecastRegionConde;
    }
}
