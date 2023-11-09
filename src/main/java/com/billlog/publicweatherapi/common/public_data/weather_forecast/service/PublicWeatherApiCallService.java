package com.billlog.publicweatherapi.common.public_data.weather_forecast.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.WeatherApiFunctionName;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.*;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicWeatherApiCallService {
    /* 공공데이터API를 이용하여 기상청 데이터를 조회한다. */
    private final CommonAPICallService commonAPICallService;
    private final MapLatLngConvertUtilService mapLatLngConvertUtilService;
    /**
     * 초단기실황 예보조회 ( getUltraSrtNcst )
     * @param page
     * @param size
     * @param baseDate
     * @param baseTime 06시 발표 ( 매 정시 )
     * @param nx
     * @param ny
     * @return
     */
    public List<UShortRealTimeForecastAPIResDto.UShortRealTimeForecastDto> getUltraShortRealTimeForecast(String page, String size, String baseDate, String baseTime, String nx, String ny){

        ObjectMapper objectMapper = new ObjectMapper();
        List<UShortRealTimeForecastAPIResDto.UShortRealTimeForecastDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Short(WeatherApiFunctionName.U_SHORT_REALTIME_FORECAST, page, size, baseDate, baseTime, nx, ny);

        try {
            UShortRealTimeForecastAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, UShortRealTimeForecastAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().getItem().stream().map(UShortRealTimeForecastAPIResDto.UShortRealTimeForecastDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new BLBusinessException("기상청 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }

    /**
     * 초단기예보 ( 6시간 뒤에까지 ) 조회 ( getUltraSrtFcst )
     * @param page
     * @param size
     * @param baseDate
     * @param baseTime 05시 발표 ( 1일 8회, 0200, 0500, 0800, 1100, 1400, 1700 2000, 2300 )
     * @param nx
     * @param ny
     * @return
     */
    public List<UShortForecastAPIResDto.UShortForecastDto> getUltraShortForecast(String page, String size, String baseDate, String baseTime, String nx, String ny){

        ObjectMapper objectMapper = new ObjectMapper();
        List<UShortForecastAPIResDto.UShortForecastDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Short(WeatherApiFunctionName.U_SHORT_FORECAST, page, size, baseDate, baseTime, nx, ny);

        try {
            UShortForecastAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, UShortForecastAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().getItem().stream().map(UShortForecastAPIResDto.UShortForecastDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new BLBusinessException("기상청 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }

    /**
     * 단기예보 조회 ( getVilageFcst )
     * @param page
     * @param size
     * @param baseDate
     * @param baseTime
     * @param nx
     * @param ny
     * @return
     */
    public List<ShortForecastAPIResDto.ShortForecastDto> getShortForecast(String page, String size, String baseDate, String baseTime, String nx, String ny){

        ObjectMapper objectMapper = new ObjectMapper();
        List<ShortForecastAPIResDto.ShortForecastDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Short(WeatherApiFunctionName.SHORT_FORECAST, page, size, baseDate, baseTime, nx, ny);

        try {
            ShortForecastAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, ShortForecastAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().getItem().stream().map(ShortForecastAPIResDto.ShortForecastDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new BLBusinessException("기상청 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }

    /**
     * 중기육상예보조회 ( getMidLandFcst )
     * @param page
     * @param size
     * @param regId
     * @param tmFc 일2회 (06:00, 18:00)
     * @return
     */
    public List<MidLandForecastAPIResDto.MidLandForecastDto> getMidLandForecast(String page, String size, String regId, String tmFc){

        ObjectMapper objectMapper = new ObjectMapper();
        List<MidLandForecastAPIResDto.MidLandForecastDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Mid(WeatherApiFunctionName.MID_LAND_FORECAST, page, size, regId, tmFc);

        try {
            MidLandForecastAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, MidLandForecastAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().getItem().stream().map(MidLandForecastAPIResDto.MidLandForecastDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new BLBusinessException("기상청 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }

    /**
     * 중기기온조회 ( getMidTa )
     * @param page
     * @param size
     * @param regId
     * @param tmFc 일2회 (06:00, 18:00)
     * @return
     */
    public List<MidTemperatureForecastAPIResDto.MidTemperatureForecastDto> getMidTemperatureForecast(String page, String size, String regId, String tmFc){

        ObjectMapper objectMapper = new ObjectMapper();
        List<MidTemperatureForecastAPIResDto.MidTemperatureForecastDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Mid(WeatherApiFunctionName.MID_TEMPERATURE_FORECAST, page, size, regId, tmFc);

        try {
            MidTemperatureForecastAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, MidTemperatureForecastAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().getItem().stream().map(MidTemperatureForecastAPIResDto.MidTemperatureForecastDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BLBusinessException("기상청 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }
}
