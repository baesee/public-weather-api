package com.billlog.publicweatherapi.domain.member.service;

import com.billlog.publicweatherapi.domain.member.dao.MemberRepository;
import com.billlog.publicweatherapi.domain.member.dao.RefreshTokenInfoRepository;
import com.billlog.publicweatherapi.domain.member.domain.Member;
import com.billlog.publicweatherapi.domain.member.domain.RefreshTokenInfo;
import com.billlog.publicweatherapi.domain.member.dto.request.MemberDto;
import com.billlog.publicweatherapi.global.jwt.JwtTokenInfoVo;
import com.billlog.publicweatherapi.global.jwt.JwtTokenProvider;
import com.billlog.publicweatherapi.global.jwt.TokenPayloadDto;
import com.billlog.publicweatherapi.global.utils.CurrentLoginUserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenInfoRepository refreshTokenInfoRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CurrentLoginUserUtil currentLoginUserUtil;

    /**
     * 리프레시 토큰 저장
     * @param memberNo
     * @param refreshToken
     */
    @Transactional
    public void setRefreshTokenInfo(Long memberNo, String refreshToken){
        RefreshTokenInfo refreshTokenInfo = RefreshTokenInfo.builder()
                .refreshToken(refreshToken)
                .memberNo(memberNo)
                .build();

        refreshTokenInfoRepository.save(refreshTokenInfo);
    }

    /**
     * 토큰 재발행 사용자의 리프레시 토큰 판단
     * @param memberNo
     * @param refreshToken
     * @return RefreshTokenNo
     */
    public Long isLoginUserRefreshToken(Long memberNo, String refreshToken){
        return refreshTokenInfoRepository.findByMemberNoAndRefreshToken(memberNo, refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자의 Refresh Token과 일치하지 않습니다."))
                .getRefreshTokenNo();
    }

    /**
     * 리프레시 토큰 삭제
     * @param refreshTokenNo
     */
    @Transactional
    public void setDeleteRefreshToken(Long refreshTokenNo){
        refreshTokenInfoRepository.deleteById(refreshTokenNo);
    }

    /**
     * 토큰 재발행
     * @param dto
     * @return
     */
    @Transactional
    public JwtTokenInfoVo reissue(MemberDto.reissueTokenRequest dto){
        // 1. 리프레시 토큰 유효성 체크
        if (dto.getRefreshToken() != null && jwtTokenProvider.validateToken(dto.getRefreshToken())) {
            // 2. 액세스 토큰에서 사용자 정보를 가져온다.
            Authentication authentication = jwtTokenProvider.getAuthentication(dto.getAccessToken());
            ObjectMapper mapper = new ObjectMapper();
            TokenPayloadDto accessTokenPayloadInfo = mapper.convertValue(authentication.getPrincipal(), TokenPayloadDto.class);

            // 3. 액세스 토큰에서 가져온 accountNo 값으로 DB에 저장된 refresh token 값과 비교한다
            isLoginUserRefreshToken(Long.parseLong(accessTokenPayloadInfo.getMemberNo()), dto.getRefreshToken());

            // 4. AccessToken 재발행 전 사용자 정보 조회
            Member memberInfo = memberRepository.findById(Long.parseLong(accessTokenPayloadInfo.getMemberNo())).orElseThrow(IllegalAccessError::new);

            // 5. Access Token 토큰 재발행
            JwtTokenInfoVo jwtTokenInfoVo = jwtTokenProvider.generateToken(authentication, memberInfo);

            // 6. Refresh Token은 기존 값을 그대로 사용하기 위해 다시 세팅
            jwtTokenInfoVo.setRefreshToken(dto.getRefreshToken());

            return jwtTokenInfoVo;
        }
        throw new IllegalArgumentException("토큰 재발행 에러 발생 : 리프레시 토큰이 유효하지 않습니다.");
    }
}
