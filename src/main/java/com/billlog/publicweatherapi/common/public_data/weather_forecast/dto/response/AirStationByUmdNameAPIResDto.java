package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class AirStationByUmdNameAPIResDto {
    /* 에어코리아 - 근접측정소 (읍면동 단위 검색) 목록 조회 API 응답 DTO */

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class resultResponse{
        @JsonProperty("response")
        private Response response;
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
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
            private List<Items> items;
            @JsonProperty("totalCount")
            private int totalCount;
            @JsonProperty("pageNo")
            private int pageNo;
            @JsonProperty("numOfRows")
            private int numOfRows;
        }
        @Getter
        @Setter
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {
            @JsonProperty("sidoName")
            private String sidoName;
            @JsonProperty("sggName")
            private String sggName;
            @JsonProperty("umdName")
            private String umdName;
            @JsonProperty("tmX")
            private String tmX;
            @JsonProperty("tmY")
            private String tmY;
        }
    }

    @Getter
    @Setter
    @Schema(description = "근접측정소 (읍면동 단위 검색) 목록 조회 응답 DTO")
    public static class AirStationByUmdNameDto{
        @Schema(description = "측정소 코드")
        private String sidoName;
        @Schema(description = "측정소 명")
        private String sggName;
        @Schema(description = "측정소 주소")
        private String umdName;
        @Schema(description = "거리(Km)")
        private String tmX;
        @Schema(description = "거리(Km)")
        private String tmY;
        public AirStationByUmdNameDto(resultResponse.Items item) {
            this.sidoName = item.getSidoName();
            this.sggName = item.getSggName();
            this.umdName = item.getUmdName();
            this.tmX = item.getTmX();
            this.tmY = item.getTmY();
        }
    }

}
