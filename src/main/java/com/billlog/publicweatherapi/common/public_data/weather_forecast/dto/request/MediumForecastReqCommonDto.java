package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class MediumForecastReqCommonDto {
    /* 중기예보 공통 요청 DTO */
    @Schema(description = "한 페이지 결과 수" , defaultValue = "10" )
    @NotBlank(message = "한 페이지 결과 수는 필수사항입니다.")
    private String numOfRows;
    @Schema(description = "페이지 번호" , defaultValue = "1" )
    @NotBlank(message = "패이지번호는 필수사항입니다.")
    private String pageNo;
    @Schema(hidden = true)
    private final String dataType = "JSON";
    @Schema(description = "예보구역코드" , example = "11B00000")
    @NotBlank(message = "예보구역코드는 필수사항입니다.")
    @Length(min = 8, max = 8, message = "예보구역코드는 8자리 입니다.")
    private String regId;
    @Schema(description = "발표시각(일 2회, 06:00 , 18:00)" , example = "202311030600")
    @NotBlank(message = "발표시각은 필수사항입니다.")
    @Length(min = 12, max = 12, message = "발표시각은 12자리 입니다.")
    private String tmFc;
}
