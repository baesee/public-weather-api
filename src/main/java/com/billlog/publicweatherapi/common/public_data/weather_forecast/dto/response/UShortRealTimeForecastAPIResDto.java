package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.ShortForecastCommonCode;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.enums.ShortForecastCommonDetailCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class UShortRealTimeForecastAPIResDto {
    /* 단기예보 - 초단기실황 조회 API 응답 DTO */

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class resultResponse{
        @JsonProperty("response")
        private Response response;
        @Getter
        @Setter
        public static class Response {
            @JsonProperty("header")
            private Header header;
            @JsonProperty("body")
            private Body body;
        }
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {
            @JsonProperty("resultCode")
            private String resultCode;
            @JsonProperty("resultMsg")
            private String resultMsg;
        }
        @Getter
        @Setter
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {
            @JsonProperty("items")
            private Items items;
        }
        @Getter
        @Setter
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {
            @JsonProperty("item")
            private List<Item> item;
        }
        @Getter
        @Setter
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Item {
            @JsonProperty("baseDate")
            private String baseDate;
            @JsonProperty("baseTime")
            private String baseTime;
            @JsonProperty("category")
            private String category;
            @JsonProperty("nx")
            private int nx;
            @JsonProperty("ny")
            private int ny;
            @JsonProperty("obsrValue")
            private String obsrValue;

        }
    }

    @Getter
    @Setter
    @Schema(description = "초단기 실황 응답 DTO")
    public static class UShortRealTimeForecastDto{
        @Schema(description = "발표일자")
        private String baseDate;
        @Schema(description = "발표시각")
        private String baseTime;
        @Schema(description = "자료구분 코드")
        private String category;
        @Schema(description = "자료구분 코드 명")
        private String categoryName;
        @Schema(description = "자료구분 상세 코드명( 'SKY' or 'PTY' 의 경우 값 존재 )")
        private String categoryDetailName;
        @Schema(description = "예보지점 X 좌표")
        private int nx;
        @Schema(description = "예보지점 Y 좌표")
        private int ny;
        @Schema(description = "실황 값")
        private String obsrValue;
        public UShortRealTimeForecastDto(resultResponse.Item item) {
            this.baseDate = item.getBaseDate();
            this.baseTime = item.getBaseTime();
            this.category = item.getCategory();
            this.categoryName = ShortForecastCommonCode.fromString(item.getCategory());

            // 특정요소의 코드값 설정( 하늘상태(SKY), 강수형태(PTY) )
            if(ShortForecastCommonCode.PTY.name().equalsIgnoreCase(item.getCategory())){
                this.categoryDetailName = ShortForecastCommonDetailCode.fromString(item.getCategory()+"_"+item.getObsrValue());
            }else if(ShortForecastCommonCode.SKY.name().equalsIgnoreCase(item.getCategory())){
                this.categoryDetailName = ShortForecastCommonDetailCode.fromString(item.getCategory()+"_"+item.getObsrValue());
            }

            this.nx = item.getNx();
            this.ny = item.getNy();

            /*
             * 풍속 표기 값 치환
             * UUU  : '+' == '동' , '-' == '서'
             * VVV  : '+' == '북' , '-' == '남'
             */
            if(ShortForecastCommonCode.UUU.name().equalsIgnoreCase(item.getCategory())){
                if(item.getObsrValue().contains("-")){
                    this.obsrValue = item.getObsrValue().replace("-", "서 ");
                }else{
                    this.obsrValue = "동 " + item.getObsrValue();
                }
            }else if(ShortForecastCommonCode.VVV.name().equalsIgnoreCase(item.getCategory())){
                if(item.getObsrValue().contains("-")){
                    this.obsrValue = item.getObsrValue().replace("-", "남 ");
                }else{
                    this.obsrValue = "북 " + item.getObsrValue();
                }
            }else if(ShortForecastCommonCode.VEC.name().equalsIgnoreCase(item.getCategory())){
                int vecValue = Integer.parseInt(item.getObsrValue());
                double windDirectionValue = (vecValue + 22.5 * 0.5) / 22.5; // 풍향 16방위 계산식
                String wind16Value = String.format("%.0f", windDirectionValue);
                this.obsrValue = ShortForecastCommonDetailCode.fromString(item.getCategory()+"_"+wind16Value);
            }else{
                this.obsrValue = item.getObsrValue();
            }
        }
    }

}
