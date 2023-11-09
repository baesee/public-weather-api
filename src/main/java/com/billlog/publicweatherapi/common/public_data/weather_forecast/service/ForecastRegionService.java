package com.billlog.publicweatherapi.common.public_data.weather_forecast.service;

import com.billlog.publicweatherapi.common.public_data.weather_forecast.dao.MidLandForecastRegionCodeRepository;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dao.MidTemperatureForecastRegionCodeRepository;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.domain.MidTemperatureForecastRegionCode;
import com.billlog.publicweatherapi.common.public_data.weather_forecast.dto.response.MidForecastRegionCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ForecastRegionService {
    /* 중기 육상/기온 예보구역 코드 관련 서비스*/
    private final MidLandForecastRegionCodeRepository midLandForecastRegionCodeRepository;
    private final MidTemperatureForecastRegionCodeRepository midTemperatureForecastRegionCodeRepository;

    /**
     * 중기 육상 및 기온 예보구역 코드 일괄 조회
     * @param address_kor
     * @return
     */
    public MidForecastRegionCodeDto getForecastRegionCode(String address_kor){
        String landForecastRegionCode;
        String temperatureForecastRegionCode;

        temperatureForecastRegionCode = getTemperatureForecastRegionCode(address_kor);
        landForecastRegionCode = getLandForecastRegionCode(address_kor);

        log.info(" 입력 받은 주소 값 : {}", address_kor);
        log.info(" 기온예보 구역 코드 : {}", temperatureForecastRegionCode);
        log.info(" 육상예보 구역 코드 : {}", landForecastRegionCode);

        return new MidForecastRegionCodeDto(landForecastRegionCode, temperatureForecastRegionCode );
    }

    // 중기육상예보 구역코드 조회
    private String getLandForecastRegionCode(String address_kor){
        String address[] = address_kor.split(" ");
        String changeAddressKeyword = address[0].contains("강원") ? changeGangwonCityReginWord(address[1]) : changeCityRegionWord(address[0]);

        return midLandForecastRegionCodeRepository.findByRegionNameContaining(changeAddressKeyword)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 중기 육상예보 지역입니다."))
                .getMidLandForecastRegionCode();

    }

    /**
     * 중기기온예보 구역코드 조회
     * @param address_kor
     * @return
     */
    private String getTemperatureForecastRegionCode(String address_kor){
        String address[] = address_kor.split(" ");
        String areaChar = address[0];
        String regionChar = address[1].substring(0, address[1].length() - 1);
        String tempforecastRegionCode;

        tempforecastRegionCode = midTemperatureForecastRegionCodeRepository.findByAreaNameContainingAndRegionNameContaining(changeCityRegionWord(areaChar), regionChar)
                .map(MidTemperatureForecastRegionCode::getMidTemperatureForecastRegionCode).orElseThrow(() -> new NoSuchElementException("존재하지 않는 기온예보 구역코드 값입니다."));
//
//        if( tempforecastRegionCode == null ){
//            /*
//             * 강원특별자치도 [ 강릉시 ] 동해대로 3544-18
//             * 서울 [ 강서구 ]오정로 443-198
//             * '시군'값으로 조회를 하기 때문에 마지막 글자 '시' or '구' or '군' 의 값은 날린다.
//             * 조회시 '강릉'으로 조회
//             */
//
//            tempforecastRegionCode = midTemperatureForecastRegionCodeRepository.findByAreaNameContainingAndRegionNameContaining(changeCityRegionWord(adreaChar), regionChar)
//                    .map(MidTemperatureForecastRegionCode::getMidTemperatureForecastRegionCode).orElseThrow(() -> new NoSuchElementException("존재하지 않는 기온예보 구역코드 값입니다."));
//        }

        return tempforecastRegionCode;
    }


    /**
     * 실제 지도에서 보내주는 주소 (ex, 전남, 경북)과 기상청 중기육상예보구역 코드값으 구역(ex, 충청남도, 전라남도) 와 상이하여 그에 따라 치환
     * @param keyword
     * @return
     */
    public String changeCityRegionWord(String keyword) {
        if (keyword.contains("전남")) {
            return "전라남도";
        } else if ("전북".contains(keyword)) {
            return "전라북도";
        } else if (keyword.contains("제주")) {
            return "제주도";
        } else if (keyword.contains("경남")) {
            return "경상남도";
        } else if (keyword.contains("경북")) {
            return "경상북도";
        } else if (keyword.contains("충북")) {
            return "충청북도";
        } else if (keyword.contains("충남")) {
            return "충청남도";
        } else if (keyword.equals("서울")) {
            return "서울";
        } else {
            return keyword;
        }
    }

    /**
     * 실제 지도에서 보내주는 '강원도'와 육상예보구역 코드값의 구역은 '강원도영서' , '강원도영동'로 나뉘어져있어 '시군구'를 기준으로 구역값을 치환한다.
     * @param keyword
     * @return
     */
    public String changeGangwonCityReginWord(String keyword){
        if (keyword.contains("철원군") || keyword.contains("화천군") || keyword.contains("양구군") ||
                keyword.contains("인제군") || keyword.contains("춘천시") || keyword.contains("홍천군") ||
                keyword.contains("횡성군") || keyword.contains("원주시") || keyword.contains("평창군") ||
                keyword.contains("영월군") || keyword.contains("정선군") || keyword.contains("이천군")) {
            return "강원도영서";
        } else if (keyword.contains("강릉시") || keyword.contains("삼척시") || keyword.contains("동해시")||
                keyword.contains("태백시") || keyword.contains("속초시") || keyword.contains("양양군") ||
                keyword.contains("고성군")) {
            return "강원도영동";
        }else{
            return keyword;
        }
    }

}
