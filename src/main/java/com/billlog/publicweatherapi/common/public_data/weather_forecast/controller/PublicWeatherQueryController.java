package com.billlog.publicweatherapi.common.public_data.weather_forecast.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.*;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.service.PublicWeatherApiCallService;
import com.billlog.publicweatherapi.global.common.reponse.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "기상청 정보 조회")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/billlog-api/pub-weather/api")
public class PublicWeatherQueryController {
    private final PublicWeatherApiCallService publicWeatherApiCallService;

    @GetMapping(value = "/v1/public-data/weather/u-short-real-time-forecast")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "초단기실황 조회 성공", content = @Content(schema = @Schema(implementation = UShortRealTimeForecastAPIResDto.UShortRealTimeForecastDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "초단기실황 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "초단기실황 조회", description = "초단기실황 조회")
    public ResponseEntity<ApiResponseDTO> ultraShortRealTimeForecast(@Schema(description = "한 페이지 결과 수", defaultValue = "10") String numOfRows, @Schema(description = "페이지 번호", defaultValue = "1")  String pageNo, @Schema(description = "발표일자", example = "20231031") String baseDate, @Schema(description = "발표시각(00분 단위)", example = "0600") String baseTime, @Schema(description = "예보지점 X 좌표(위도 값도 가능)") String nx, @Schema(description = "예보지점 Y 좌표(경도 값도 가능)") String ny) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(publicWeatherApiCallService.getUltraShortRealTimeForecast(pageNo, numOfRows, baseDate, baseTime, nx, ny)));
    }

    @GetMapping(value = "/v1/public-data/weather/u-short-forecast")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "초단기예보 조회 성공", content = @Content(schema = @Schema(implementation = UShortForecastAPIResDto.UShortForecastDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "초단기실황 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "초단기예보 조회", description = "초단기예보 조회")
    public ResponseEntity<ApiResponseDTO> ultraShortForecast(@Schema(description = "한 페이지 결과 수", defaultValue = "10") String numOfRows, @Schema(description = "페이지 번호", defaultValue = "1")  String pageNo, @Schema(description = "발표일자", example = "20231031") String baseDate, @Schema(description = "발표시각(30분 단위)", example = "0630") String baseTime, @Schema(description = "예보지점 X 좌표(위도 값도 가능)") String nx, @Schema(description = "예보지점 Y 좌표(경도 값도 가능)") String ny) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(publicWeatherApiCallService.getUltraShortForecast(pageNo, numOfRows, baseDate, baseTime, nx, ny)));
    }

    @GetMapping(value = "/v1/public-data/weather/short-forecast")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "단기예보 조회 성공", content = @Content(schema = @Schema(implementation = ShortForecastAPIResDto.ShortForecastDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "단기예보 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "단기예보 조회", description = "단기예보 조회")
    public ResponseEntity<ApiResponseDTO> shortForecast(@Schema(description = "한 페이지 결과 수", defaultValue = "10") String numOfRows, @Schema(description = "페이지 번호", defaultValue = "1")  String pageNo, @Schema(description = "발표일자", example = "20231031") String baseDate, @Schema(description = "발표시각(0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300) 1일 8회", example = "0500") String baseTime, @Schema(description = "예보지점 X 좌표(위도 값도 가능)") String nx, @Schema(description = "예보지점 Y 좌표(경도 값도 가능)") String ny) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(publicWeatherApiCallService.getShortForecast(pageNo, numOfRows, baseDate, baseTime, nx, ny)));
    }

    @GetMapping(value = "/v1/public-data/weather/mid-land-forecast")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "중기육상예보 조회 성공", content = @Content(schema = @Schema(implementation = MidLandForecastAPIResDto.MidLandForecastDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "중기육상예보 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "중기육상예보 조회", description = "중기육상예보 조회")
    public ResponseEntity<ApiResponseDTO> midLandForecast(@Valid @Schema(description = "한 페이지 결과 수", defaultValue = "10") String numOfRows, @Schema(description = "페이지 번호", defaultValue = "1") String pageNo, @Schema(description = "예보구역코드", example = "11B00000") String regId, @Schema(description = "발표시각(일 2회, 06:00 , 18:00)", example = "202311020600") String tmFc) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(publicWeatherApiCallService.getMidLandForecast(pageNo, numOfRows, regId, tmFc)));
    }

    @GetMapping(value = "/v1/public-data/weather/mid-temperature-forecast")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "중기기온예보 조회 성공", content = @Content(schema = @Schema(implementation = MidTemperatureForecastAPIResDto.MidTemperatureForecastDto.class))),
            @ApiResponse(responseCode = "4XX,5XX", description = "중기기온예보 조회 실패", content = @Content(schema = @Schema(implementation = Void.class))),
    })
    @Operation(summary = "중기기온예보 조회", description = "중기기온예보 조회")
    public ResponseEntity<ApiResponseDTO> midTemperatureForecast(@Valid @Schema(description = "한 페이지 결과 수", defaultValue = "10") String numOfRows, @Schema(description = "페이지 번호", defaultValue = "1") String pageNo, @Schema(description = "예보구역코드", example = "11B10101") String regId, @Schema(description = "발표시각(일 2회, 06:00 , 18:00)", example = "202311020600") String tmFc) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.SUCCESS(publicWeatherApiCallService.getMidTemperatureForecast(pageNo, numOfRows, regId, tmFc)));
    }
}
