package com.billlog.publicweatherapi.common.public_data.weather_forecast.service;

import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.ShortForecastCommonCode;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.WeatherApiFunctionName;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.DefaultPageSize.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherCombineService {
    /* 서비스에 맞게 날씨 정보를 가공하는 서비스 */

    private final PublicWeatherApiCallService publicWeatherApiCallService;
    private final ForecastRegionService forecastRegionService;


    /**
     * 특정 지역(nx, ny)의 현재시간 기준 날씨정보 조회
     *
     * @param nx       예보지점 X 좌료
     * @param ny       예보지점 Y 좌료
     * @return 해당 지역의 날씨정보
     */
    public CurrentWeatherDto currentWeatherInLatLng(String nx, String ny) {

        // 초단기실황 예보일자 조회
        WeatherApiBaseDateTimeDto uShortRealForecastDateAndTime = getForecastDateAndTime(WeatherApiFunctionName.U_SHORT_REALTIME_FORECAST);

        // 초단기실황 : 기온, 1시간 강수량, 풍속, 풍향, 습도 , 강수형태
        List<UShortRealTimeForecastAPIResDto.UShortRealTimeForecastDto> ultraShortRealTimeForecast =
                publicWeatherApiCallService.getUltraShortRealTimeForecast(DEFAULT_PAGE_1.getDefaultValue(), DEFAULT_PAGE_SIZE_10.getDefaultValue(), uShortRealForecastDateAndTime.getBaseDate(), uShortRealForecastDateAndTime.getBaseTime(), nx, ny);

        // 초단기예보 예보일자 조회
        WeatherApiBaseDateTimeDto uShortForecastDateAndTime = getForecastDateAndTime(WeatherApiFunctionName.U_SHORT_FORECAST);

        // 초단기예보 : 하늘상태
        List<UShortForecastAPIResDto.UShortForecastDto> ultraShortForecast = publicWeatherApiCallService.getUltraShortForecast(DEFAULT_PAGE_1.getDefaultValue(), DEFAULT_PAGE_SIZE_100.getDefaultValue(), uShortForecastDateAndTime.getBaseDate(), uShortForecastDateAndTime.getBaseTime(), nx, ny);
        Optional<UShortForecastAPIResDto.UShortForecastDto> skyStateByFindFirst = ultraShortForecast.stream().filter(v -> ShortForecastCommonCode.SKY.name().equalsIgnoreCase(v.getCategory())).findFirst();

        CurrentWeatherDto currentWeatherDto = new CurrentWeatherDto(ultraShortRealTimeForecast);

        if (skyStateByFindFirst.isPresent())
            currentWeatherDto.setSkyState(skyStateByFindFirst.get().getCategoryDetailName());

        return currentWeatherDto;
    }

    /**
     * 특정 지역(nx, ny)의 시간별 날씨 ( 기온/하늘 , 강수확률/강수량, 풍향/풍속, 습도 )
     * @param nx
     * @param ny
     * @return
     */
    public List<HourlyweatherDto> hourlyWeatherInLatLng(String nx, String ny) {

        // 단기예보 예보일자 조회
        WeatherApiBaseDateTimeDto shortForecastDateAndTime = getForecastDateAndTime(WeatherApiFunctionName.SHORT_FORECAST);

        // 단기예보 : 특정 시간 이후 3일까지의 데이터를 조회.
        List<ShortForecastAPIResDto.ShortForecastDto> shortForecast = publicWeatherApiCallService.getShortForecast(DEFAULT_PAGE_1.getDefaultValue(), DEFAULT_PAGE_SIZE_1000.getDefaultValue(), shortForecastDateAndTime.getBaseDate(), shortForecastDateAndTime.getBaseTime(), nx, ny);

        // API에서 받아온 값으로 타입값별로 분류 및 Map으로 변환
        Map<String, ShortForecastAPIResDto.ShortForecastDto> temperatureMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.TMP.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> sortedTemperatureMap = temperatureMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, LinkedHashMap::new));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> skyStateMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.SKY.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> rainFallProbabilityMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.POP.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> rainFallMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.PCP.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> humidityMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.REH.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> windDirectionMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.VEC.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        Map<String, ShortForecastAPIResDto.ShortForecastDto> windSpeedMap = shortForecast.stream()
                .filter(v -> ShortForecastCommonCode.WSD.name().equals(v.getCategory()))
                .collect(Collectors.toMap(dto -> dto.getFcstDate() + dto.getFcstTime(), dto -> dto));

        List<HourlyweatherDto> combinedList = temperatureMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .filter(entry -> skyStateMap.containsKey(entry.getKey()))
                .map(entry -> {
                    ShortForecastAPIResDto.ShortForecastDto temperatureDto = entry.getValue();
                    ShortForecastAPIResDto.ShortForecastDto skyStateDto = skyStateMap.get(entry.getKey());
                    ShortForecastAPIResDto.ShortForecastDto rainFallProbabilityDto = rainFallProbabilityMap.get(entry.getKey());
                    ShortForecastAPIResDto.ShortForecastDto rainFallDto = rainFallMap.get(entry.getKey());
                    ShortForecastAPIResDto.ShortForecastDto humidityDto = humidityMap.get(entry.getKey());
                    ShortForecastAPIResDto.ShortForecastDto windDirectionDto = windDirectionMap.get(entry.getKey());
                    ShortForecastAPIResDto.ShortForecastDto windSpeedDto = windSpeedMap.get(entry.getKey());

                    return HourlyweatherDto.builder()
                            .forecastDate(temperatureDto.getFcstDate())
                            .forecastTime(temperatureDto.getFcstTime())
                            .skyState(skyStateDto.getCategoryDetailName())
                            .temperature(temperatureDto.getFcstValue())
                            .humidity(humidityDto.getFcstValue())
                            .rainFallProbability(rainFallProbabilityDto.getFcstValue())
                            .rainfall(rainFallDto.getFcstValue())
                            .windDirection(windDirectionDto.getFcstValue())
                            .windSpeedValue(windSpeedDto.getFcstValue())
                            .build();
                })
                .collect(Collectors.toList());

        return combinedList;

    }

    /**
     * 예보일로부터 4일 후부터 10일까지의 기간에 대한 기상전망, 기온, 강수확률 정보를 제공합니다.
     * @param address_kor
     * @return
     */
    public WeeklyWeatherDto weeklyWeatherByRegId(String address_kor) {

        // 구역 코드 조회
        MidForecastRegionCodeDto forecastRegionCode = forecastRegionService.getForecastRegionCode(address_kor);

        // 중기예보 예보일자(tmFc) 조회
        WeatherApiBaseDateTimeDto forecastDateAndTime = getForecastDateAndTime(WeatherApiFunctionName.MID_LAND_FORECAST);

        // 중기육상예보
        List<MidLandForecastAPIResDto.MidLandForecastDto> midLandForecast = publicWeatherApiCallService.getMidLandForecast(DEFAULT_PAGE_1.getDefaultValue(), DEFAULT_PAGE_SIZE_10.getDefaultValue(), forecastRegionCode.getLandForecastRegionConde(), forecastDateAndTime.getTmFc());
        // 중기기온예보
        List<MidTemperatureForecastAPIResDto.MidTemperatureForecastDto> midTemperatureForecast = publicWeatherApiCallService.getMidTemperatureForecast(DEFAULT_PAGE_1.getDefaultValue(), DEFAULT_PAGE_SIZE_10.getDefaultValue(), forecastRegionCode.getTemperatureForecastRegionConde(), forecastDateAndTime.getTmFc());

        return WeeklyWeatherDto.builder()
                .after3DayAMRainFallProbability(midLandForecast.get(0).getRnSt3Am())
                .after3DayPMRainFallProbability(midLandForecast.get(0).getRnSt3Pm())
                .after3DayAMWeather(midLandForecast.get(0).getWf3Am())
                .after3DayPMWeather(midLandForecast.get(0).getWf3Pm())
                .after3DayLowestTemperature(midTemperatureForecast.get(0).getTaMin3())
                .after3DayHighestTemperature(midTemperatureForecast.get(0).getTaMax3())

                .after4DayAMRainFallProbability(midLandForecast.get(0).getRnSt4Am())
                .after4DayPMRainFallProbability(midLandForecast.get(0).getRnSt4Pm())
                .after4DayAMWeather(midLandForecast.get(0).getWf4Am())
                .after4DayPMWeather(midLandForecast.get(0).getWf4Pm())
                .after4DayLowestTemperature(midTemperatureForecast.get(0).getTaMin4())
                .after4DayHighestTemperature(midTemperatureForecast.get(0).getTaMax4())

                .after5DayAMRainFallProbability(midLandForecast.get(0).getRnSt5Am())
                .after5DayPMRainFallProbability(midLandForecast.get(0).getRnSt5Pm())
                .after5DayAMWeather(midLandForecast.get(0).getWf5Am())
                .after5DayPMWeather(midLandForecast.get(0).getWf5Pm())
                .after5DayLowestTemperature(midTemperatureForecast.get(0).getTaMin5())
                .after5DayHighestTemperature(midTemperatureForecast.get(0).getTaMax5())

                .after6DayAMRainFallProbability(midLandForecast.get(0).getRnSt6Am())
                .after6DayPMRainFallProbability(midLandForecast.get(0).getRnSt6Pm())
                .after6DayAMWeather(midLandForecast.get(0).getWf6Am())
                .after6DayPMWeather(midLandForecast.get(0).getWf6Pm())
                .after6DayLowestTemperature(midTemperatureForecast.get(0).getTaMin6())
                .after6DayHighestTemperature(midTemperatureForecast.get(0).getTaMax6())

                .after7DayAMRainFallProbability(midLandForecast.get(0).getRnSt7Am())
                .after7DayPMRainFallProbability(midLandForecast.get(0).getRnSt7Pm())
                .after7DayAMWeather(midLandForecast.get(0).getWf7Am())
                .after7DayPMWeather(midLandForecast.get(0).getWf7Pm())
                .after7DayLowestTemperature(midTemperatureForecast.get(0).getTaMin7())
                .after7DayHighestTemperature(midTemperatureForecast.get(0).getTaMax7())

                .after8DayRainFallProbability(midLandForecast.get(0).getRnSt8())
                .after8DayWeather(midLandForecast.get(0).getWf8())
                .after8DayLowestTemperature(midTemperatureForecast.get(0).getTaMin8())
                .after8DayHighestTemperature(midTemperatureForecast.get(0).getTaMax8())

                .after9DayRainFallProbability(midLandForecast.get(0).getRnSt9())
                .after9DayWeather(midLandForecast.get(0).getWf9())
                .after9DayLowestTemperature(midTemperatureForecast.get(0).getTaMin9())
                .after9DayHighestTemperature(midTemperatureForecast.get(0).getTaMax9())

                .after10DayRainFallProbability(midLandForecast.get(0).getRnSt10())
                .after10DayWeather(midLandForecast.get(0).getWf10())
                .after10DayLowestTemperature(midTemperatureForecast.get(0).getTaMin10())
                .after10DayHighestTemperature(midTemperatureForecast.get(0).getTaMax10())
                .build();
    }

    //TODO : 초단기실황,초단기예보,단기예보,중기육상예보,중기기온조회에서 사용될 기준시간 값을 세팅해준다. 응답값 : 발표일시
    public WeatherApiBaseDateTimeDto getForecastDateAndTime(WeatherApiFunctionName weatherApiType){
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd,HH:mm"));
        String date = now.split(",")[0];
        String time = now.split(",")[1];
        String hour = time.split(":")[0];
        String minute = time.split(":")[1];
        String customBaseTime;

        if(WeatherApiFunctionName.U_SHORT_REALTIME_FORECAST.equals(weatherApiType)){
            int iMinute = Integer.parseInt(minute);
            if ( iMinute < 40 ){
                int targetHour = Integer.parseInt(hour) - 1;
                String prefix = targetHour < 10 ? "0" : "" ;
                hour =  prefix  + String.valueOf(targetHour);
            }
            customBaseTime = hour + "00";

            log.info(" 초단기실황 -> baseDate : {}, baseTime : {}", date, customBaseTime );
            return WeatherApiBaseDateTimeDto.builder()
                    .baseTime(customBaseTime)
                    .baseDate(date)
                    .build();
        }else if(WeatherApiFunctionName.U_SHORT_FORECAST.equals(weatherApiType)){
            int iMinute = Integer.parseInt(minute);
            if ( iMinute < 45 ){
                int targetHour = Integer.parseInt(hour) - 1;
                String prefix = targetHour < 10 ? "0" : "" ;
                hour =  prefix  + String.valueOf(targetHour);
            }

            customBaseTime = hour + "30";

            log.info(" 초단기예보 -> baseDate : {}, baseTime : {}", date, customBaseTime );

            return WeatherApiBaseDateTimeDto.builder()
                    .baseTime(customBaseTime)
                    .baseDate(date)
                    .build();
        }else if(WeatherApiFunctionName.SHORT_FORECAST.equals(weatherApiType)){
            int iHour = Integer.parseInt(hour);
            if ( iHour >= 2 && iHour < 5 ){
                hour = "02";
            }else if(iHour >= 5 && iHour < 8){
                hour = "05";
            }else if(iHour >= 8 && iHour < 11){
                hour = "08";
            }else if(iHour >= 11 && iHour < 14){
                hour = "11";
            }else if(iHour >= 14 && iHour < 17){
                hour = "14";
            }else if(iHour >= 17 && iHour < 20){
                hour = "17";
            }else if(iHour >= 20 && iHour < 23){
                hour = "20";
            }else {
                hour = "23";
            }

            customBaseTime = hour + "00";

            log.info(" 단기예보 -> baseDate : {}, baseTime : {}", date, customBaseTime );

            return WeatherApiBaseDateTimeDto.builder()
                    .baseTime(customBaseTime)
                    .baseDate(date)
                    .build();

        }else if(WeatherApiFunctionName.MID_LAND_FORECAST.equals(weatherApiType) || WeatherApiFunctionName.MID_TEMPERATURE_FORECAST.equals(weatherApiType)){
            int iHour = Integer.parseInt(hour);
            if ( iHour >= 6 && iHour < 18 ){
                hour = "06";
            }else{
                hour = "18";
            }

            log.info(" 중기예보 [{}]-> tmFc : {}",weatherApiType.getDescription(),date+hour+"00" );

            return WeatherApiBaseDateTimeDto.builder()
                    .tmFc(date+hour+"00")
                    .build();
        }
        return null;
    }

}
