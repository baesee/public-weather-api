package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import lombok.Getter;

@Getter
public class LatXLngY {
    /* GRID X,Y 도는 위경도 값으로 변한한 값을 담을 그릇*/
    public double lat;
    public double lng;

    public double x;
    public double y;
}
