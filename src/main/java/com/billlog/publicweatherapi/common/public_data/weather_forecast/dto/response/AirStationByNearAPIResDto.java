package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class AirStationByNearAPIResDto {
    /* 에어코리아 - 근접측정소 목록 조회 API 응답 DTO */

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
            @JsonProperty("stationCode")
            private String stationCode;
            @JsonProperty("stationName")
            private String stationName;
            @JsonProperty("addr")
            private String addr;
            @JsonProperty("tm")
            private String tm;
        }
    }

    @Getter
    @Setter
    @Schema(description = "근접측정소 목록 조회 응답 DTO")
    public static class AirStationByNearDto{
        @Schema(description = "측정소 코드")
        private String stationCode;
        @Schema(description = "측정소 명")
        private String stationName;
        @Schema(description = "측정소 주소")
        private String addr;
        @Schema(description = "거리(Km)")
        private String tm;
        public AirStationByNearDto(resultResponse.Items item) {
            this.stationCode = item.getStationCode();
            this.stationName = item.getStationName();
            this.addr = item.getAddr();
            this.tm = item.getTm();
        }
    }

}
