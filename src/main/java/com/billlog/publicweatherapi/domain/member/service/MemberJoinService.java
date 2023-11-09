package com.billlog.publicweatherapi.domain.member.service;

import com.billlog.publicweatherapi.domain.member.dao.MemberRepository;
import com.billlog.publicweatherapi.domain.member.domain.Member;
import com.billlog.publicweatherapi.domain.member.dto.request.MemberDto;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import com.billlog.publicweatherapi.global.jwt.JwtTokenInfoVo;
import com.billlog.publicweatherapi.global.utils.RandomNicknameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberJoinService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberLoginService memberLoginService;
    private final RandomNicknameGenerator randomNicknameGenerator;

    public JwtTokenInfoVo socialSignup(MemberDto.JoinRequest dto) {

        Member member = memberJoin(dto);

        // 회원가입 이후 로그인 처리
        MemberDto.loginRequest loginRequest = MemberDto.loginRequest.builder()
                .memberId(member.getMemberId())
                .password(" ")
                .build();

        return memberLoginService.socialLogin(loginRequest);
    }
    @Transactional
    public Member memberJoin(MemberDto.JoinRequest dto){

        if(memberRepository.existsByMemberIdAndAccountType(dto.getSocialId(), dto.getAccountType())){
            throw new BLBusinessException("이미 가입된 사용자입니다.");
        }

        int retryCount = 5;
        String randomNickname = randomNicknameGenerator.getRandomNickname();

        if(memberRepository.existsByNickname(randomNickname)){ // 닉네임이 중복이라면 총 5번까지 리트라이
            for( int i = 0 ; i < retryCount ; i ++){
                String tmpRandomNickname = randomNicknameGenerator.getRandomNickname();
                if(!memberRepository.existsByNickname(tmpRandomNickname)) {
                    randomNickname = tmpRandomNickname;
                    break;
                }
                if (i == retryCount - 1){
                    randomNickname = "AnonymousUser" + (int)(Math.random()*10000);
                }
            }
        }

        Member member = Member.builder()
                .memberId(dto.getSocialId())
                .password(passwordEncoder.encode(" ")) // 소셜 사용자의 경우 패스워드가 공백.
                .nickname(randomNickname)
                .accountType(dto.getAccountType())
                .policyAgree(dto.getPolicyAgree())
                .gender(StringUtils.hasText(dto.getGender()) ? dto.getGender() : "C") // 성별 없을 경우 "C'로 인입
                .build();

        return memberRepository.save(member);
    }



}
