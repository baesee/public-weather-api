package com.billlog.publicweatherapi.domain.member.service;

import com.billlog.publicweatherapi.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberUtilService {
    private final MemberRepository memberRepository;
    public boolean isDuplicateId(String memberId){
        return memberRepository.existsByMemberId(memberId);
    }
    public boolean isDuplicateNickname(String nickname){ return memberRepository.existsByNickname(nickname); }
}
