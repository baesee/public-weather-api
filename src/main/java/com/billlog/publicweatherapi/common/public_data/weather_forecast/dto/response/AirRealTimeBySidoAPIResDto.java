package com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class AirRealTimeBySidoAPIResDto {
    /* 에어코리아 - 시도별 실시간 측정정보 조회 API 응답 DTO */

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
            @JsonProperty("stationName")
            private String stationName;
            @JsonProperty("stationCode")
            private String stationCode;
            @JsonProperty("mangName")
            private String mangName;
            @JsonProperty("sidoName")
            private String sidoName;
            @JsonProperty("dataTime")
            private String dataTime;
            @JsonProperty("pm10Value")
            private String pm10Value;
            @JsonProperty("pm10Grade")
            private String pm10Grade;
            @JsonProperty("pm10Grade1h")
            private String pm10Grade1h;
            @JsonProperty("pm25Value")
            private String pm25Value;
            @JsonProperty("pm25Grade")
            private String pm25Grade;
            @JsonProperty("pm25Grade1h")
            private String pm25Grade1h;
            @JsonProperty("pm10Flag")
            private String pm10Flag;
            @JsonProperty("pm25Flag")
            private String pm25Flag;
        }
    }

    @Getter
    @Setter
    @Schema(description = "측정소별 실시간 측정정보 조회 응답 DTO")
    public static class AirRealTimeBySidoDto{
        @Schema(description = "측정일시")
        private String dataTime;
        @Schema(description = "측정소명")
        private String stationName;
        @Schema(description = "측정소 코드")
        private String stationCode;
        @Schema(description = "측정망 정보")
        private String mangName;
        @Schema(description = "시도명")
        private String sidoName;
        @Schema(description = "미세먼지(PM10) 농도")
        private String pm10Value;
        @Schema(description = "미세먼지(PM2.5) 농도")
        private String pm25Value;
        @Schema(description = "미세먼지(PM10) 24시간 등급")
        private String pm10Grade;
        @Schema(description = "미세먼지(PM10) 24시간 등급값의 의미 ")
        private String pm10GradeMean;
        @Schema(description = "미세먼지(PM10) 1시간 등급")
        private String pm10Grade1h;
        @Schema(description = "미세먼지(PM10) 1시간 등급값의 의미")
        private String pm10Grade1hMean;
        @Schema(description = "미세먼지(PM2.5) 24시간 등급")
        private String pm25Grade;
        @Schema(description = "미세먼지(PM2.5) 24시간 등급값의 의미")
        private String pm25GradeMean;
        @Schema(description = "미세먼지(PM2.5) 1시간 등급")
        private String pm25Grade1h;
        @Schema(description = "미세먼지(PM2.5) 1시간 등급의 의미")
        private String pm25Grade1hMean;
        @Schema(description = "미세먼지(PM10) 플래그")
        private String pm10Flag;
        @Schema(description = "미세먼지(PM2.5) 플래그")
        private String pm25Flag;

        public AirRealTimeBySidoDto(resultResponse.Items item) {
            this.dataTime = item.getDataTime();
            this.stationName = item.getStationName();
            this.stationCode = item.getStationCode();
            this.sidoName = item.getSidoName();
            this.mangName = item.getMangName();
            this.pm10Value = item.getPm10Value();
            this.pm25Value = item.getPm25Value();
            this.pm10Grade = item.getPm10Grade();
            this.pm10Grade1h = item.getPm10Grade1h();
            this.pm25Grade = item.getPm25Grade();
            this.pm25Grade1h = item.getPm25Grade1h();
            this.pm10GradeMean = getGradeMeanString(item.getPm10Grade());
            this.pm10Grade1hMean = getGradeMeanString(item.getPm10Grade1h());
            this.pm25GradeMean = getGradeMeanString(item.getPm25Grade());
            this.pm25Grade1hMean = getGradeMeanString(item.getPm25Grade1h());
            this.pm10Flag = item.getPm10Flag();
            this.pm25Flag = item.getPm25Flag();
        }

        public String getGradeMeanString(String grade){
            if("1".equals(grade)){
                return "좋음";
            }else if("2".equals(grade)){
                return "보통";
            }else if("3".equals(grade)){
                return "나쁨";
            }else if("4".equals(grade)){
                return "매우나쁨";
            }else{
                return "Err Grade Mean";
            }
        }
    }

}
