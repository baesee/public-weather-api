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

public class MidLandForecastAPIResDto {
    /* 중기예뵤 -중기육상예보 조회 API 응답 DTO */

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
            @JsonProperty("regId")
            private String regId;
            @JsonProperty("rnSt3Am")
            private String rnSt3Am;
            @JsonProperty("rnSt3Pm")
            private String rnSt3Pm;
            @JsonProperty("rnSt4Am")
            private String rnSt4Am;
            @JsonProperty("rnSt4Pm")
            private String rnSt4Pm;
            @JsonProperty("rnSt5Am")
            private String rnSt5Am;
            @JsonProperty("rnSt5Pm")
            private String rnSt5Pm;
            @JsonProperty("rnSt6Am")
            private String rnSt6Am;
            @JsonProperty("rnSt6Pm")
            private String rnSt6Pm;
            @JsonProperty("rnSt7Am")
            private String rnSt7Am;
            @JsonProperty("rnSt7Pm")
            private String rnSt7Pm;
            @JsonProperty("rnSt8")
            private String rnSt8;
            @JsonProperty("rnSt9")
            private String rnSt9;
            @JsonProperty("rnSt10")
            private String rnSt10;
            @JsonProperty("wf3Am")
            private String wf3Am;
            @JsonProperty("wf3Pm")
            private String wf3Pm;
            @JsonProperty("wf4Am")
            private String wf4Am;
            @JsonProperty("wf4Pm")
            private String wf4Pm;
            @JsonProperty("wf5Am")
            private String wf5Am;
            @JsonProperty("wf5Pm")
            private String wf5Pm;
            @JsonProperty("wf6Am")
            private String wf6Am;
            @JsonProperty("wf6Pm")
            private String wf6Pm;
            @JsonProperty("wf7Am")
            private String wf7Am;
            @JsonProperty("wf7Pm")
            private String wf7Pm;
            @JsonProperty("wf8")
            private String wf8;
            @JsonProperty("wf9")
            private String wf9;
            @JsonProperty("wf10")
            private String wf10;
        }
    }

    @Getter
    @Setter
    @Schema(description = "중기 육상예보 조회 응답 DTO")
    public static class MidLandForecastDto{
        @Schema(description = "예보구역코드")
        private String regId;
        @Schema(description = "3일 후 오전 강수 확률")
        private String rnSt3Am;
        @Schema(description = "3일 후 오후 강수 확률")
        private String rnSt3Pm;
        @Schema(description = "4일 후 오전 강수 확률")
        private String rnSt4Am;
        @Schema(description = "4일 후 오후 강수 확률")
        private String rnSt4Pm;
        @Schema(description = "5일 후 오전 강수 확률")
        private String rnSt5Am;
        @Schema(description = "5일 후 오후 강수 확률")
        private String rnSt5Pm;
        @Schema(description = "6일 후 오전 강수 확률")
        private String rnSt6Am;
        @Schema(description = "6일 후 오후 강수 확률")
        private String rnSt6Pm;
        @Schema(description = "7일 후 오전 강수 확률")
        private String rnSt7Am;
        @Schema(description = "7일 후 오후 강수 확률")
        private String rnSt7Pm;
        @Schema(description = "8일 후 강수 확률")
        private String rnSt8;
        @Schema(description = "9일 후 강수 확률")
        private String rnSt9;
        @Schema(description = "10일 후 강수 확률")
        private String rnSt10;
        @Schema(description = "3일 후 오전 날씨 예보")
        private String wf3Am;
        @Schema(description = "3일 후 오후 날씨 예보")
        private String wf3Pm;
        @Schema(description = "4일 후 오전 날씨 예보")
        private String wf4Am;
        @Schema(description = "4일 후 오후 날씨 예보")
        private String wf4Pm;
        @Schema(description = "5일 후 오전 날씨 예보")
        private String wf5Am;
        @Schema(description = "5일 후 오후 날씨 예보")
        private String wf5Pm;
        @Schema(description = "6일 후 오전 날씨 예보")
        private String wf6Am;
        @Schema(description = "6일 후 오후 날씨 예보")
        private String wf6Pm;
        @Schema(description = "7일 후 오전 날씨 예보")
        private String wf7Am;
        @Schema(description = "7일 후 오후 날씨 예보")
        private String wf7Pm;
        @Schema(description = "8일 후 오후 날씨 예보")
        private String wf8;
        @Schema(description = "9일 후 오후 날씨 예보")
        private String wf9;
        @Schema(description = "10일 후 오후 날씨 예보")
        private String wf10;

        public MidLandForecastDto(resultResponse.Item item) {
            this.regId = item.getRegId();
            this.rnSt3Am = item.getRnSt3Am();
            this.rnSt3Pm = item.getRnSt3Pm();
            this.rnSt4Am = item.getRnSt4Am();
            this.rnSt4Pm = item.getRnSt4Pm();
            this.rnSt5Am = item.getRnSt5Am();
            this.rnSt5Pm = item.getRnSt5Pm();
            this.rnSt6Am = item.getRnSt6Am();
            this.rnSt6Pm = item.getRnSt6Pm();
            this.rnSt7Am = item.getRnSt7Am();
            this.rnSt7Pm = item.getRnSt7Pm();
            this.rnSt8 = item.getRnSt8();
            this.rnSt9 = item.getRnSt9();
            this.rnSt10 = item.getRnSt10();
            this.wf3Am = item.getWf3Am();
            this.wf3Pm = item.getWf3Pm();
            this.wf4Am = item.getWf4Am();
            this.wf4Pm = item.getWf4Pm();
            this.wf5Am = item.getWf5Am();
            this.wf5Pm = item.getWf5Pm();
            this.wf6Am = item.getWf6Am();
            this.wf6Pm = item.getWf6Pm();
            this.wf7Am = item.getWf7Am();
            this.wf7Pm = item.getWf7Pm();
            this.wf8 = item.getWf8();
            this.wf9 = item.getWf9();
            this.wf10 = item.getWf10();
        }
    }

}
