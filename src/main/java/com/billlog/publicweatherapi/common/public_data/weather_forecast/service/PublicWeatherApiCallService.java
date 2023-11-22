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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    /**
     * 에어코리아 ( 대기정보 )
     * 측정소별 실시간 측정정보 조회 (getMsrstnAcctoRltmMesureDnsty )
     * @param page
     * @param size
     * @param stationName : 측정소 이름 (ex, 종로구)
     * @param dataTerm : 요청 데이터 기간( 'DAILY', 'MONTH', '3MONTH' )
     * @param ver : 버전별 상세 결과 ( '1.3' )
     * @return
     */
    public List<AirRealTimeByStationAPIResDto.AirRealTimeByStationDto> getRealTimeByStation(String page, String size, String stationName, String dataTerm, String ver){

        ObjectMapper objectMapper = new ObjectMapper();
        List<AirRealTimeByStationAPIResDto.AirRealTimeByStationDto> resultList = new ArrayList<>();

        try {
            String encodeStationName = URLEncoder.encode(stationName, "utf8");
            String jsonResult = commonAPICallService.GET_Air_Station(WeatherApiFunctionName.AIR_REALTIME_BY_STATION, page, size, encodeStationName, dataTerm, ver);

            AirRealTimeByStationAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, AirRealTimeByStationAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().stream().map(AirRealTimeByStationAPIResDto.AirRealTimeByStationDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 값 Parsing 중 에러 발생");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 파라미터 인코딩 작업중 에러 발생");
        }

        return resultList;
    }

    /**
     * 에어코리아 ( 대기정보 )
     * 시도별 실시간 측정정보 조회 (getCtprvnRltmMesureDnsty )
     * @param page
     * @param size
     * @param sidoName
     * @param ver
     * @return
     */
    public List<AirRealTimeBySidoAPIResDto.AirRealTimeBySidoDto> getRealTimeBySido(String page, String size, String sidoName, String ver){

        ObjectMapper objectMapper = new ObjectMapper();
        List<AirRealTimeBySidoAPIResDto.AirRealTimeBySidoDto> resultList = new ArrayList<>();

        try {
            String encodeSidoName = URLEncoder.encode(sidoName, "utf8");
            String jsonResult = commonAPICallService.GET_Air_Sido(WeatherApiFunctionName.AIR_REALTIME_BY_SIDO, page, size, encodeSidoName, ver);

            AirRealTimeBySidoAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, AirRealTimeBySidoAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().stream().map(AirRealTimeBySidoAPIResDto.AirRealTimeBySidoDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 값 Parsing 중 에러 발생");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 파라미터 인코딩 작업중 에러 발생");
        }

        return resultList;
    }

    /**
     * 에어코리아 ( 측정소 정보 )
     * 근접측정소 목록 조회
     * @param tmX
     * @param tmY
     * @param ver
     * @return
     */
    public List<AirStationByNearAPIResDto.AirStationByNearDto> getStationByNear(String tmX, String tmY, String ver){

        ObjectMapper objectMapper = new ObjectMapper();
        List<AirStationByNearAPIResDto.AirStationByNearDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Air_StationByNear(WeatherApiFunctionName.AIR_STATION_BY_NEAR, tmX, tmY, ver);

        try {
            AirStationByNearAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, AirStationByNearAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().stream().map(AirStationByNearAPIResDto.AirStationByNearDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }

    /**
     * 에어코리아 ( 측정소 정보 )
     * 입력받은 읍면동 측정소 목록 조회
     * @param page
     * @param size
     * @param umdName
     * @return
     */
    public List<AirStationByUmdNameAPIResDto.AirStationByUmdNameDto> getStationByUmdName(String page, String size, String umdName){

        ObjectMapper objectMapper = new ObjectMapper();
        List<AirStationByUmdNameAPIResDto.AirStationByUmdNameDto> resultList = new ArrayList<>();

        try {
            String encodeUmdName = URLEncoder.encode(umdName, "utf8");
            String jsonResult = commonAPICallService.GET_Air_StationByUmdName(WeatherApiFunctionName.AIR_STATION_BY_UMD, page, size, encodeUmdName);

            AirStationByUmdNameAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, AirStationByUmdNameAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().stream().map(AirStationByUmdNameAPIResDto.AirStationByUmdNameDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 값 Parsing 중 에러 발생");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BLBusinessException("에어코리아 API 파라미터 인코딩 작업중 에러 발생");
        }

        return resultList;
    }


    /**
     * 기상청 자외선지수 조회
     * @param page
     * @param size
     * @param areaNo
     * @param time
     * @return
     */
    public List<LivingWeatherAPIResDto.LivingWeatherDto> getLivingUv(String page, String size, String areaNo, String time){

        ObjectMapper objectMapper = new ObjectMapper();
        List<LivingWeatherAPIResDto.LivingWeatherDto> resultList = new ArrayList<>();
        String jsonResult = commonAPICallService.GET_Living_Uv(WeatherApiFunctionName.LIVING_UV, page, size, areaNo, time);

        try {
            LivingWeatherAPIResDto.resultResponse resultResponse = objectMapper.readValue(jsonResult, LivingWeatherAPIResDto.resultResponse.class);
            if(!resultResponse.getResponse().getHeader().getResultCode().equals("00")) return null;
            resultList = resultResponse.getResponse().getBody().getItems().getItem().stream().map(LivingWeatherAPIResDto.LivingWeatherDto::new).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BLBusinessException("기상청 API 값 Parsing 중 에러 발생");
        }

        return resultList;
    }
}
