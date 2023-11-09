package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class MidTemperatureForecastAPIResDto {
    /* 중기예뵤 -중기기온예보 조회 API 응답 DTO */

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

            @JsonProperty("taMin3")
            private String taMin3;

            @JsonProperty("taMin3Low")
            private String taMin3Low;

            @JsonProperty("taMin3High")
            private String taMin3High;

            @JsonProperty("taMax3")
            private String taMax3;

            @JsonProperty("taMax3Low")
            private String taMax3Low;

            @JsonProperty("taMax3High")
            private String taMax3High;

            @JsonProperty("taMin4")
            private String taMin4;

            @JsonProperty("taMin4Low")
            private String taMin4Low;

            @JsonProperty("taMin4High")
            private String taMin4High;

            @JsonProperty("taMax4")
            private String taMax4;

            @JsonProperty("taMax4Low")
            private String taMax4Low;

            @JsonProperty("taMax4High")
            private String taMax4High;

            @JsonProperty("taMin5")
            private String taMin5;

            @JsonProperty("taMin5Low")
            private String taMin5Low;

            @JsonProperty("taMin5High")
            private String taMin5High;

            @JsonProperty("taMax5")
            private String taMax5;

            @JsonProperty("taMax5Low")
            private String taMax5Low;

            @JsonProperty("taMax5High")
            private String taMax5High;

            @JsonProperty("taMin6")
            private String taMin6;

            @JsonProperty("taMin6Low")
            private String taMin6Low;

            @JsonProperty("taMin6High")
            private String taMin6High;

            @JsonProperty("taMax6")
            private String taMax6;

            @JsonProperty("taMax6Low")
            private String taMax6Low;

            @JsonProperty("taMax6High")
            private String taMax6High;

            @JsonProperty("taMin7")
            private String taMin7;

            @JsonProperty("taMin7Low")
            private String taMin7Low;

            @JsonProperty("taMin7High")
            private String taMin7High;

            @JsonProperty("taMax7")
            private String taMax7;

            @JsonProperty("taMax7Low")
            private String taMax7Low;

            @JsonProperty("taMax7High")
            private String taMax7High;

            @JsonProperty("taMin8")
            private String taMin8;

            @JsonProperty("taMin8Low")
            private String taMin8Low;

            @JsonProperty("taMin8High")
            private String taMin8High;

            @JsonProperty("taMax8")
            private String taMax8;

            @JsonProperty("taMax8Low")
            private String taMax8Low;

            @JsonProperty("taMax8High")
            private String taMax8High;

            @JsonProperty("taMin9")
            private String taMin9;

            @JsonProperty("taMin9Low")
            private String taMin9Low;

            @JsonProperty("taMin9High")
            private String taMin9High;

            @JsonProperty("taMax9")
            private String taMax9;

            @JsonProperty("taMax9Low")
            private String taMax9Low;

            @JsonProperty("taMax9High")
            private String taMax9High;

            @JsonProperty("taMin10")
            private String taMin10;

            @JsonProperty("taMin10Low")
            private String taMin10Low;

            @JsonProperty("taMin10High")
            private String taMin10High;

            @JsonProperty("taMax10")
            private String taMax10;

            @JsonProperty("taMax10Low")
            private String taMax10Low;

            @JsonProperty("taMax10High")
            private String taMax10High;
        }
    }

    @Getter
    @Setter
    @Schema(description = "중기 기온예보 조회 응답 DTO")
    public static class MidTemperatureForecastDto{
        @Schema(description = "예보구역코드")
        private String regId;

        @Schema(description = "3일 후 예상최저기온(℃)")
        private String taMin3;

        @Schema(description = "3일 후 예상최저기온 하한 범위")
        private String taMin3Low;

        @Schema(description = "3일 후 예상최저기온 상한 범위")
        private String taMin3High;

        @Schema(description = "3일 후 예상최고기온(℃)")
        private String taMax3;

        @Schema(description = "3일 후 예상최고기온 하한 범위")
        private String taMax3Low;

        @Schema(description = "3일 후 예상최고기온 상한 범위")
        private String taMax3High;

        @Schema(description = "4일 후 예상최저기온(℃)")
        private String taMin4;

        @Schema(description = "4일 후 예상최저기온 하한 범위")
        private String taMin4Low;

        @Schema(description = "4일 후 예상최저기온 상한 범위")
        private String taMin4High;

        @Schema(description = "4일 후 예상최고기온(℃)")
        private String taMax4;

        @Schema(description = "4일 후 예상최고기온 하한 범위")
        private String taMax4Low;

        @Schema(description = "4일 후 예상최고기온 상한 범위")
        private String taMax4High;

        @Schema(description = "5일 후 예상최저기온(℃)")
        private String taMin5;

        @Schema(description = "5일 후 예상최저기온 하한 범위")
        private String taMin5Low;

        @Schema(description = "5일 후 예상최저기온 상한 범위")
        private String taMin5High;

        @Schema(description = "5일 후 예상최고기온(℃)")
        private String taMax5;

        @Schema(description = "5일 후 예상최고기온 하한 범위")
        private String taMax5Low;

        @Schema(description = "5일 후 예상최고기온 상한 범위")
        private String taMax5High;

        @Schema(description = "6일 후 예상최저기온(℃)")
        private String taMin6;

        @Schema(description = "6일 후 예상최저기온 하한 범위")
        private String taMin6Low;

        @Schema(description = "6일 후 예상최저기온 상한 범위")
        private String taMin6High;

        @Schema(description = "6일 후 예상최고기온(℃)")
        private String taMax6;

        @Schema(description = "6일 후 예상최고기온 하한 범위")
        private String taMax6Low;

        @Schema(description = "6일 후 예상최고기온 상한 범위")
        private String taMax6High;

        @Schema(description = "7일 후 예상최저기온(℃)")
        private String taMin7;

        @Schema(description = "7일 후 예상최저기온 하한 범위")
        private String taMin7Low;

        @Schema(description = "7일 후 예상최저기온 상한 범위")
        private String taMin7High;

        @Schema(description = "7일 후 예상최고기온(℃)")
        private String taMax7;

        @Schema(description = "7일 후 예상최고기온 하한 범위")
        private String taMax7Low;

        @Schema(description = "7일 후 예상최고기온 상한 범위")
        private String taMax7High;

        @Schema(description = "8일 후 예상최저기온(℃)")
        private String taMin8;

        @Schema(description = "8일 후 예상최저기온 하한 범위")
        private String taMin8Low;

        @Schema(description = "8일 후 예상최저기온 상한 범위")
        private String taMin8High;

        @Schema(description = "8일 후 예상최고기온(℃)")
        private String taMax8;

        @Schema(description = "8일 후 예상최고기온 하한 범위")
        private String taMax8Low;

        @Schema(description = "8일 후 예상최고기온 상한 범위")
        private String taMax8High;

        @Schema(description = "9일 후 예상최저기온(℃)")
        private String taMin9;

        @Schema(description = "9일 후 예상최저기온 하한 범위")
        private String taMin9Low;

        @Schema(description = "9일 후 예상최저기온 상한 범위")
        private String taMin9High;

        @Schema(description = "9일 후 예상최고기온(℃)")
        private String taMax9;

        @Schema(description = "9일 후 예상최고기온 하한 범위")
        private String taMax9Low;

        @Schema(description = "9일 후 예상최고기온 상한 범위")
        private String taMax9High;

        @Schema(description = "10일 후 예상최저기온(℃)")
        private String taMin10;

        @Schema(description = "10일 후 예상최저기온 하한 범위")
        private String taMin10Low;

        @Schema(description = "10일 후 예상최저기온 상한 범위")
        private String taMin10High;

        @Schema(description = "10일 후 예상최고기온(℃)")
        private String taMax10;

        @Schema(description = "10일 후 예상최고기온 하한 범위")
        private String taMax10Low;

        @Schema(description = "10일 후 예상최고기온 상한 범위")
        private String taMax10High;

        public MidTemperatureForecastDto(resultResponse.Item item) {
            this.regId = item.getRegId();
            this.taMin3 = item.getTaMin3();
            this.taMin3Low = item.getTaMin3Low();
            this.taMin3High = item.getTaMin3High();
            this.taMax3 = item.getTaMax3();
            this.taMax3Low = item.getTaMax3Low();
            this.taMax3High = item.getTaMax3High();
            this.taMin4 = item.getTaMin4();
            this.taMin4Low = item.getTaMin4Low();
            this.taMin4High = item.getTaMin4High();
            this.taMax4 = item.getTaMax4();
            this.taMax4Low = item.getTaMax4Low();
            this.taMax4High = item.getTaMax4High();
            this.taMin5 = item.getTaMin5();
            this.taMin5Low = item.getTaMin5Low();
            this.taMin5High = item.getTaMin5High();
            this.taMax5 = item.getTaMax5();
            this.taMax5Low = item.getTaMax5Low();
            this.taMax5High = item.getTaMax5High();
            this.taMin6 = item.getTaMin6();
            this.taMin6Low = item.getTaMin6Low();
            this.taMin6High = item.getTaMin6High();
            this.taMax6 = item.getTaMax6();
            this.taMax6Low = item.getTaMax6Low();
            this.taMax6High = item.getTaMax6High();
            this.taMin7 = item.getTaMin7();
            this.taMin7Low = item.getTaMin7Low();
            this.taMin7High = item.getTaMin7High();
            this.taMax7 = item.getTaMax7();
            this.taMax7Low = item.getTaMax7Low();
            this.taMax7High = item.getTaMax7High();
            this.taMin8 = item.getTaMin8();
            this.taMin8Low = item.getTaMin8Low();
            this.taMin8High = item.getTaMin8High();
            this.taMax8 = item.getTaMax8();
            this.taMax8Low = item.getTaMax8Low();
            this.taMax8High = item.getTaMax8High();
            this.taMin9 = item.getTaMin9();
            this.taMin9Low = item.getTaMin9Low();
            this.taMin9High = item.getTaMin9High();
            this.taMax9 = item.getTaMax9();
            this.taMax9Low = item.getTaMax9Low();
            this.taMax9High = item.getTaMax9High();
            this.taMin10 = item.getTaMin10();
            this.taMin10Low = item.getTaMin10Low();
            this.taMin10High = item.getTaMin10High();
            this.taMax10 = item.getTaMax10();
            this.taMax10Low = item.getTaMax10Low();
            this.taMax10High = item.getTaMax10High();
        }
    }

}
