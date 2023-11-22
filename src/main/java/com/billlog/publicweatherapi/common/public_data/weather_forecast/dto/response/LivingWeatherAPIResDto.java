package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class LivingWeatherAPIResDto {
    /* 기상청 - 생활기상지수(자외선) 조회 API 응답 DTO */

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
            @JsonProperty("code")
            private String code;
            @JsonProperty("areaNo")
            private String areaNo;
            @JsonProperty("date")
            private String date;
            @JsonProperty("h0")
            private String h0;
        }
    }

    @Getter
    @Setter
    @Schema(description = "자외선 지후 조회 응답 DTO")
    public static class LivingWeatherDto{
        @Schema(description = "예보구역코드")
        private String code;
        @Schema(description = "3일 후 예상최저기온(℃)")
        private String areaNo;
        @Schema(description = "3일 후 예상최저기온 하한 범위")
        private String date;
        @Schema(description = "현 시점 자외선 지수")
        private String currentUvScore;
        @Schema(description = "자외선 지수 단계명")
        private String uvStageName;

        public LivingWeatherDto(resultResponse.Item item) {
            this.code = item.getCode();
            this.areaNo = item.getAreaNo();
            this.date = item.getDate();
            this.currentUvScore = item.getH0();
            this.uvStageName = getUvStageNameString(item.getH0());
        }

        public String getUvStageNameString(String currentUvScore){
            int iCurrentUvScore = Integer.parseInt(currentUvScore);
            if ( iCurrentUvScore < 3 ) {
                return "낮음";
            }else if( iCurrentUvScore >= 3 && iCurrentUvScore < 6 ){
                return "보통";
            }else if( iCurrentUvScore >= 6 && iCurrentUvScore < 8 ){
                return "높음";
            }else if( iCurrentUvScore >= 8 && iCurrentUvScore < 11 ){
                return "매우높음";
            }else if( iCurrentUvScore >= 11 ){
                return "위험";
            }else{
                return "Err UV Stage";
            }
        }
    }

}
