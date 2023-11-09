package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class ShortForecastReqCommonDto {
    /* 단기예보 공통 요청 DTO */
    @Schema(description = "한 페이지 결과 수" , defaultValue = "10" )
    @NotBlank(message = "한 페이지 결과 수는 필수사항입니다.")
    private String numOfRows;
    @Schema(description = "페이지 번호" , defaultValue = "1" )
    @NotBlank(message = "패이지번호는 필수사항입니다.")
    private String pageNo;
    @Schema(hidden = true)
    private final String dataType = "JSON";
    @Schema(description = "발표일자" , example = "20231031")
    @NotBlank(message = "발표일자는 필수사항입니다.")
    @Length(min = 8, max = 8, message = "발표일자는 8자리 입니다.")
    private String baseDate;
    @Schema(description = "발표시각(30분 단위)" , example = "0630")
    @NotBlank(message = "발표시각은 필수사항입니다.")
    @Length(min = 4, max = 4, message = "발표시각은 4자리 입니다.")
    private String baseTime;
    @Schema(description = "예보지점 X 좌표")
    @NotBlank(message = "예보지점 X 좌표는 필수사항입니다.")
    private String nx;
    @Schema(description = "예보지점 Y 좌표")
    @NotBlank(message = "예보지점 Y 좌표는 필수사항입니다.")
    private String ny;
}
