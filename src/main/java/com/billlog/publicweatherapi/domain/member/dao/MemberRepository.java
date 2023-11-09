package com.billlog.publicweatherapi.domain.member.dao;

import com.billlog.publicweatherapi.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);
    boolean existsByNickname(String nickname);
    Boolean existsByMemberIdAndAccountType(String memberId, String accountType);
    Optional<Member> findByMemberId(String memberId);
}
