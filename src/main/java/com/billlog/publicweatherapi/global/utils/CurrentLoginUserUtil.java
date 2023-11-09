package com.billlog.publicweatherapi.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import com.billlog.publicweatherapi.global.jwt.TokenPayloadDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentLoginUserUtil {

    /**
     * 로그인 여부 판단
     * @return true : 로긴유저, false : 비로그인 유저
     */
    public boolean isLoginUser() {
        return !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    /**
     * 로그인한(토큰정보가 유요한)사용자의 정보를 가져온다.
     * @return TokenPayloadDto
     */
    public TokenPayloadDto getCurrentLoginUserInfo() {
        if(!isLoginUser()){
            throw new BLBusinessException("로그인이 필요한 서비스입니다. 로그인을 진행해주세요.");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(principal, TokenPayloadDto.class);
    }

    /**
     * 로그인 한 사용자의 memberNo 값을 가져온다.
     * @return memberNo
     */
    public Long getMemberNoByLoginUser() {
        return Long.valueOf(getCurrentLoginUserInfo().getMemberNo());
    }

}
