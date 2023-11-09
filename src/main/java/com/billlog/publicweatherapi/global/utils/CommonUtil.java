package com.billlog.publicweatherapi.global.utils;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@Component
public class CommonUtil {

    /**
     * 설명 : 숫자 난수 생성
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     * ex ) 6자리 중복이 가능 한 수 요청 ( 요청 결과 예시 : 114957)
     * commonUtil.getRandNumber(6,1);
     */
    public String getRandNumber(int len, int dupCd ) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }

    /**
     * 설명 : 복잡도 증가시킨 난수 생성 (영대소문자 + 숫자) , (특수문자)
     * @param planTextLen
     * @param numberTextLen
     * @param specialTextLen
     * @return
     * commonUtil.getComplexRandNumber(12);
     */
    public String getComplexRandNumber(int planTextLen, int numberTextLen, int specialTextLen) {

        String numStr = ""; //난수가 저장될 변수
        numStr += RandomStringUtils.random(planTextLen, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        numStr += RandomStringUtils.random(numberTextLen, "1234567890");
        numStr += RandomStringUtils.random(specialTextLen, "!@#$%^&*");

        return numStr;
    }

    /**
     * 설명 : 복잡도 증가시킨 난수 생성 (영대소문자 + 숫자) , (특수문자)
     */
    public String getUniqueId() {
        String uniqueId = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        uniqueId = sdf.format(dateTime.getTime());

        //yyyymmddhh24missSSS_랜덤문자4개
        uniqueId = uniqueId+"_"+RandomStringUtils.randomAlphanumeric(4);

        return uniqueId;
    }
}
