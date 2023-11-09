package com.billlog.publicweatherapi.common.public_data.weather_forecast.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MidLandForecastRegionCode {
    /* 중기육상 에보구역 코드 엔티티 */
    @Id @Column(name="mid_land_forecast_region_code")
    private String midLandForecastRegionCode; // 예보구역 코드
    @NotNull
    private String regionName; // 예보 구역
}
