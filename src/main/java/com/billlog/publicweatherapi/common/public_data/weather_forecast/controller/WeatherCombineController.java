package com.billlog.publicweatherapi.common.public_data.weather_forecast.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.CurrentWeatherDto;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.HourlyweatherDto;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.WeeklyWeatherDto;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.service.WeatherCombineService;
import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "날씨(기상청X, 가공데이터) 정보 조회")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/pub-weather/api")
public class WeatherCombineController {
    private final WeatherCombineService weatherCombineService;

    @GetMapping(value = "/v1/weather/current")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "현재 기상정보 조회 성공", content = @Content(schema = @Schema(implementation = CurrentWeatherDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "현재 기상정보 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "현재 기상정보 조회", description = "현재 기상정보 조회")
    public ResponseEntity<ApiResponseDTO> currentWeatherInfo(@Schema(description = "예보지점 X 좌표(위도 값도 가능)") String nx, @Schema(description = "예보지점 Y 좌표(경도 값도 가능)") String ny) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(weatherCombineService.currentWeatherInLatLng(nx, ny)));
    }

    @GetMapping(value = "/v1/weather/hourly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "시간별 날씨 조회 성공", content = @Content(schema = @Schema(implementation = HourlyweatherDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "시간별 날씨 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "시간별 날씨 조회", description = "시간별 날씨 조회")
    public ResponseEntity<ApiResponseDTO> hourlyWeatherInfo(@Schema(description = "예보지점 X 좌표(위도 값도 가능)") String nx, @Schema(description = "예보지점 Y 좌표(경도 값도 가능)") String ny) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(weatherCombineService.hourlyWeatherInLatLng(nx, ny)));
    }

    @GetMapping(value = "/v1/weather/weekly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "주간 날씨 조회 성공", content = @Content(schema = @Schema(implementation = WeeklyWeatherDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "주간 날씨 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "주간 날씨 조회", description = "주간 날씨 조회")
    public ResponseEntity<ApiResponseDTO> weeklyWeatherInfo(@Schema(description = "해당 위치 주소 ( 띄어쓰기로 구분 ex, 경남 창원시 진해구 수제로 36 / 강원특별자치도 강릉시 동해대로 3544-18)", example = "전남 무안군 청계면 서호로 162") String address_kor) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(weatherCombineService.weeklyWeatherByRegId(address_kor)));
    }

}
