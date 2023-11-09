# Billlog Public Weather API V1

## 1. 준비작업
1. MySql 기동 필요 ( DB : pub_weather )
2. application-local.yml의 기상청 API KEY값 세팅 {PUBLIC_DATA_FORECAST_KEY}
3. Boot 기동시 [ ddl-auto : update ]로 엔티티 생성 됨.
4. 데이터 삽입(insert) 실행
   - 중기 육상예보 코드 삽입 쿼리 : src/main/resources/sql/mid_land_forecast_region_code.sql
   - 중기 기온예보 코드 삽입 쿼리 : src/main/resources/sql/mid_temperature_forecast_regin_code.sql


## 2. 기동 확인
1. 부트 기동 후 해당 url로 스웨거 접속
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

## 환경변수 정의
| No  |환경변수명| 설정값                | 설명                   |
|:---:|:---|:-------------------|:---------------------|
|  1  |SPRING_PROFILES_ACTIVE_ENV| local,release,prod | 환경별 프로파일 설정          |
|  2  |PUBLIC_DATA_FORECAST_KEY| api_key            | 공공데이터 포탈에서 발급받은 API키 |
