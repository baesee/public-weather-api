package com.billlog.publicweatherapi.domain.member.service;

import com.billlog.publicweatherapi.domain.member.dao.MemberRepository;
import com.billlog.publicweatherapi.domain.member.domain.Member;
import com.billlog.publicweatherapi.domain.member.dto.request.MemberDto;
import com.billlog.publicweatherapi.global.jwt.JwtTokenInfoVo;
import com.billlog.publicweatherapi.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@RequiredArgsConstructor
public class MemberLoginService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    /**
     * 소셜 로그인
     * @param dto
     * @return
     */
    @Transactional
    public JwtTokenInfoVo socialLogin(MemberDto.loginRequest dto){

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getMemberId(), dto.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3.사용자 정보 조회 ( token payload 에 담기 위해 선 조회 )
        Member member = memberRepository.findByMemberId(dto.getMemberId()).orElseThrow(IllegalAccessError::new);

        // 4. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenInfoVo tokenInfo = jwtTokenProvider.generateToken(authenticate, member);

        // 5. 로그인 한 사용자에게 발급된 리프레시 토큰을 저장한다.
        refreshTokenService.setRefreshTokenInfo(member.getMemberNo(), tokenInfo.getRefreshToken());

        return tokenInfo;
    }
}
