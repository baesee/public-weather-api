package com.billlog.publicweatherapi.common.public_data.weather_forecast.dao;

import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.MidLandForecastRegionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MidLandForecastRegionCodeRepository extends JpaRepository<MidLandForecastRegionCode, String> {
    Optional<MidLandForecastRegionCode> findByRegionNameContaining(String address_kor);
}
