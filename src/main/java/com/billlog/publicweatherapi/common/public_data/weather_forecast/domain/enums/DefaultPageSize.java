package com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums;

import lombok.Getter;

@Getter
public enum DefaultPageSize {
    DEFAULT_PAGE_SIZE_10("10"),
    DEFAULT_PAGE_SIZE_100("100"),
    DEFAULT_PAGE_SIZE_1000("1000"),
    DEFAULT_PAGE_1("1");

    private final String defaultValue;
    DefaultPageSize(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
