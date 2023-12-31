package com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums;

import lombok.Getter;

@Getter
public enum WeatherApiFunctionName {
    /* 공공데이터 API 기상청 데이터 조회시 상세기능명 */
    U_SHORT_REALTIME_FORECAST("getUltraSrtNcst", "초단기실황조회"),
    U_SHORT_FORECAST("getUltraSrtFcst", "초단기예보조회"),
    SHORT_FORECAST("getVilageFcst","단기예보조회"),
    MID_LAND_FORECAST("getMidLandFcst","중기육상예보조회"),
    MID_TEMPERATURE_FORECAST("getMidTa","중기기온조회"),
    AIR_REALTIME_BY_STATION("getMsrstnAcctoRltmMesureDnsty","측정소별 실시간 측정정보 조회"),
    AIR_REALTIME_BY_SIDO("getCtprvnRltmMesureDnsty","시도별 실시간 측정정보 조회"),
    AIR_STATION_BY_NEAR("getNearbyMsrstnList","근접측정소 목록 정보 조회"),
    AIR_STATION_BY_UMD("getTMStdrCrdnt","TM 기준좌표(읍면동) 조회"),
    LIVING_UV("getUVIdxV4","자외선지수 조회");
    private final String type;
    private final String description;
    WeatherApiFunctionName(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
