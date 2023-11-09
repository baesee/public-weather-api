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
}
