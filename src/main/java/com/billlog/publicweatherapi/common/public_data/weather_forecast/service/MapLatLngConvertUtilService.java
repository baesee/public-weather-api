package com.billlog.publicweatherapi.common.public_data.weather_forecast.service;

import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.LatXLngY;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.proj4j.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MapLatLngConvertUtilService {
    /* 위경도 값을 받아 GRID x,y좌표로 컨버팅 서비스 */

    /**
     * 좌표변환 ( mode : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
     * @param mode
     * @param lat_X
     * @param lng_Y
     * @return
     */
    public LatXLngY convertGRID_GPS(String mode, double lat_X, double lng_Y ){

        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if ("TO_GRID".equals(mode)) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;

    }

    /**
     * 위도, 경도 값을 받아 TM 좌표계 값으로 변경
     * 사용처 : 에어코리아 측정소 조회
     * @param lat_X
     * @param lng_Y
     * @return
     */
    public LatXLngY convertTM(double lat_X, double lng_Y){

        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem soruce = crsFactory.createFromName("epsg:4326");
        CoordinateReferenceSystem target = crsFactory.createFromName("epsg:2097");

        CoordinateTransformFactory ctFactory =  new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(soruce, target);

        ProjCoordinate result = new ProjCoordinate();
        transform.transform(new ProjCoordinate(lat_X, lng_Y), result);
        System.out.println("x : "+ result.x + " y :" + result.y);

        LatXLngY rs = new LatXLngY();
        rs.lat = result.x;
        rs.lng = result.y;

        return rs;
    }
}
