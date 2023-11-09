package com.billlog.publicweatherapi.common.public_data.weather_forecast.dao;

import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.MidTemperatureForecastRegionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MidTemperatureForecastRegionCodeRepository extends JpaRepository<MidTemperatureForecastRegionCode, String> {
    Optional<MidTemperatureForecastRegionCode> findByAreaNameContainingAndRegionNameContaining(String areaName, String reginName);
}
