package com.billlog.publicweatherapi.common.public_data.weather_forecast.service;

import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.WeatherApiFunctionName;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.LatXLngY;
import com.billlog.publicweatherapi.global.config.WebClientConfig;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonAPICallService {
    private final WebClientConfig webClientConfig;
    private final MapLatLngConvertUtilService mapLatLngConvertUtilService;
    @Value("${public.data.forecast.short.baseUrl}")
    private String shortBaseUrl;
    @Value("${public.data.forecast.key}")
    private String forecastKey;
    @Value("${public.data.forecast.medium.baseUrl}")
    private String mediumBaseUrl;
    @Value("${public.data.forecast.airkorea.baseUrl}")
    private String airkoreaBaseUrl;
    @Value("${public.data.forecast.airkorea-station.baseUrl}")
    private String airkoreaStationBaseUrl;
    @Value("${public.data.forecast.living.baseUrl}")
    private String livingBaseUrl;

    /**
     * 기상청  단기예보 HTTP GET 호출 , 응답 형태 : 목록 (List)
     * @param weatherApiFunctionName
     * @param page
     * @param size
     * @param baseDate
     * @param baseTime
     * @param nx
     * @param ny
     * @return
     */
    public String GET_Short(WeatherApiFunctionName weatherApiFunctionName,
                            String page, String size, String baseDate, String baseTime, String nx, String ny){

        String baseUrl = shortBaseUrl;
        String key = forecastKey;

        // nx, ny 값 판단, 위경도 값으로 인입되었을 경우( 값에 '점(.)' 이 있는지 판단) 컨버팅 진행
        if(nx.contains(".") && ny.contains(".")) {
            LatXLngY latXLngY = convertToNxNy(nx, ny);
            nx = String.format("%.0f",latXLngY.x);
            ny = String.format("%.0f",latXLngY.y);
        }

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&numOfRows=" + size +
                        "&pageNo=" + page +
                        "&dataType=JSON" +
                        "&base_date=" + baseDate +
                        "&base_time=" + baseTime +
                        "&nx=" + nx +
                        "&ny=" + ny)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("기상청 단기예보 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("기상청 단기예보 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }

    /**
     * 기상청 중기예보 HTTP GET 호출 , 응답 형태 : 목록 (List)
     * @param weatherApiFunctionName
     * @param page
     * @param size
     * @param regId
     * @param tmFc
     * @return
     */
    public String GET_Mid(WeatherApiFunctionName weatherApiFunctionName,
                            String page, String size, String regId, String tmFc){

        String baseUrl = mediumBaseUrl;
        String key = forecastKey;

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&numOfRows=" + size +
                        "&pageNo=" + page +
                        "&dataType=JSON" +
                        "&regId=" + regId +
                        "&tmFc=" + tmFc)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("기상청 중기예보 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("기상청 중기예보 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }

    /**
     * 에어코리아 측정소별 실시간 측정정보 조회 HTTP GET 호출, 응답형태 : 목록 (List)
     * @param weatherApiFunctionName
     * @param page
     * @param size
     * @param stationName ( URLEncode 필 )
     * @param dataTerm
     * @param ver
     * @return
     */
    public String GET_Air_Station(WeatherApiFunctionName weatherApiFunctionName,
                                  String page, String size, String stationName, String dataTerm, String ver){

        String baseUrl = airkoreaBaseUrl;
        String key = forecastKey;

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&numOfRows=" + size +
                        "&pageNo=" + page +
                        "&returnType=json" +
                        "&stationName=" + stationName +
                        "&dataTerm=" + dataTerm +
                        "&ver=" + ver)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }


    /**
     * 에어코리아 시도별 실시간 측정정보 조회 HTTP GET 호출, 응답형태 : 목록 (List)
     * @param weatherApiFunctionName
     * @param page
     * @param size
     * @param sidoName ( URLEncode 필 )
     * @param ver
     * @return
     */
    public String GET_Air_Sido(WeatherApiFunctionName weatherApiFunctionName,
                               String page, String size, String sidoName, String ver){

        String baseUrl = airkoreaBaseUrl;
        String key = forecastKey;

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&numOfRows=" + size +
                        "&pageNo=" + page +
                        "&returnType=json" +
                        "&sidoName=" + sidoName +
                        "&ver=" + ver)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }

    /**
     * 에어코리아 근접측정소 TM좌표 주변 층적소 정보 목록 조회
     * @param weatherApiFunctionName
     * @param tmX
     * @param tmY
     * @param ver
     * @return
     */
    public String GET_Air_StationByNear(WeatherApiFunctionName weatherApiFunctionName,
                                        String tmX, String tmY, String ver){

        String baseUrl = airkoreaStationBaseUrl;
        String key = forecastKey;

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&tmX=" + tmX +
                        "&tmY=" + tmY +
                        "&returnType=json" +
                        "&ver=" + ver)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }

    /**
     * 에어코리아 근접측정소 입력받은 읍면동 값 층적소 정보 목록 조회
     * @param weatherApiFunctionName
     * @param pageNo
     * @param numOfRows
     * @param umdName
     * @return
     */
    public String GET_Air_StationByUmdName(WeatherApiFunctionName weatherApiFunctionName,
                                           String pageNo, String numOfRows, String umdName){

        String baseUrl = airkoreaStationBaseUrl;
        String key = forecastKey;

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&pageNo=" + pageNo +
                        "&numOfRows=" + numOfRows +
                        "&returnType=json" +
                        "&umdName=" + umdName)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("에어코리아 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }

    //기상청 생활기상지수 자외선 지수 HTTP GET 호출

    public String GET_Living_Uv(WeatherApiFunctionName weatherApiFunctionName,
                                String page, String size, String areaNo, String time){

        String baseUrl = livingBaseUrl;
        String key = forecastKey;

        return webClientConfig.webClient().get()
                .uri(baseUrl + "/" + weatherApiFunctionName.getType() + "?serviceKey=" + key +
                        "&numOfRows=" + size +
                        "&pageNo=" + page +
                        "&dataType=JSON" +
                        "&areaNo=" + areaNo +
                        "&time=" + time)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new BLBusinessException("기상청 생활기상지수 데이터 조회시 요청에러 발생")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new BLBusinessException("기상청 생활기상지수 데이터 조회시 서버에러 발생")))
                .bodyToFlux(new ParameterizedTypeReference<String>() {
                })
                .blockFirst();
    }

    /**
     * MAP에서 받아온 위경도 값을 기상청에서 사용되는 좌표 (nx, ny)값으로 컨버팅
     * @param lat_x
     * @param lng_y
     * @return
     */
    private LatXLngY convertToNxNy(String lat_x, String lng_y){
        double latX = Double.parseDouble(lat_x);
        double lngY = Double.parseDouble(lng_y);

        return mapLatLngConvertUtilService.convertGRID_GPS("TO_GRID", latX, lngY);
    }

    /**
     * 위도, 경도 값을 받아 TM 좌표계 값으로 변경
     * 사용처 : 에어코리아 측정소 조회
     * @param lat_x
     * @param lng_y
     * @return
     */
    public LatXLngY convertToTM(String lat_x, String lng_y){
        double latX = Double.parseDouble(lat_x);
        double lngY = Double.parseDouble(lng_y);
        return mapLatLngConvertUtilService.convertTM(latX, lngY);
    }
}
