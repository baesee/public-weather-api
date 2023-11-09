package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.ShortForecastCommonCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrentWeatherDto {
    /* 현재기상 정보 DTO */
    @Schema(description = "강수형태 (없음, 비, 비/눈, 눈, 빗방울, 빗방울눈날림, 눈날림)")
    private String rainType;
    @Schema(description = "기온")
    private String temperature;
    @Schema(description = "습도 (%)")
    private String humidity;
    @Schema(description = "1시간 강수량(mm)")
    private String rainfall;
    @Schema(description = "풍향")
    private String windDirection;
    @Schema(description = "풍속(m/s)값")
    private String windSpeedValue;
    @Schema(description = "풍속 의미(약하다, 약간강하다, 강하다, 매우 강하다)")
    private String windSpeedDescription;
    @Schema(description = "하늘상태(맑음, 구름많음, 흐림)")
    private String skyState;

    public CurrentWeatherDto(List<UShortRealTimeForecastAPIResDto.UShortRealTimeForecastDto> dto) {
        dto.forEach( value ->{
            if(value.getCategory().equals(ShortForecastCommonCode.PTY.name())){
                this.rainType = value.getCategoryDetailName();
            }
            if(value.getCategory().equals(ShortForecastCommonCode.T1H.name())){
                this.temperature = value.getObsrValue();
            }
            if(value.getCategory().equals(ShortForecastCommonCode.REH.name())){
                this.humidity = value.getObsrValue();
            }
            if(value.getCategory().equals(ShortForecastCommonCode.RN1.name())){
                this.rainfall = value.getObsrValue();
            }
            if(value.getCategory().equals(ShortForecastCommonCode.VEC.name())){
                this.windDirection = value.getObsrValue();
            }
            if(value.getCategory().equals(ShortForecastCommonCode.WSD.name())){
                double dValue = Double.parseDouble(value.getObsrValue());
                if( dValue < 4 ){
                    this.windSpeedDescription = "바람이 약하다.";
                }else if( dValue >= 4 && dValue < 9){
                    this.windSpeedDescription = "바람이 약간 약하다.";
                }else if( dValue >= 9 && dValue < 14){
                    this.windSpeedDescription = "바람이 강하다.";
                }else if( dValue >= 14){
                    this.windSpeedDescription = "바람이 매우 강하다.";
                }
                this.windSpeedValue = value.getObsrValue();
            }
        });
    }
}
