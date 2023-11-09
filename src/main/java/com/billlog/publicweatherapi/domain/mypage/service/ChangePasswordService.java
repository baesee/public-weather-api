package com.billlog.publicweatherapi.domain.mypage.service;

import com.billlog.publicweatherapi.domain.member.dao.MemberRepository;
import com.billlog.publicweatherapi.domain.member.domain.Member;
import com.billlog.publicweatherapi.domain.mypage.dto.request.MyInfoDto;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import com.billlog.publicweatherapi.global.utils.CurrentLoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChangePasswordService {
    private final MemberRepository memberRepository;
    private final CurrentLoginUserUtil currentLoginUserUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void setChangePassword(MyInfoDto.passwordChangeRequest dto) {
        Long memberNo = currentLoginUserUtil.getMemberNoByLoginUser();

        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NoSuchElementException("가입되지 않은 회원정보입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new BLBusinessException("현재 비밀번호가 다릅니다.");
        }

        member.changePassword(passwordEncoder.encode(dto.getNewPassword()));
    }
}
