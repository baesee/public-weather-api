package com.billlog.publicweatherapi.domain.member.dao;


import com.billlog.publicweatherapi.domain.member.domain.RefreshTokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenInfoRepository extends JpaRepository<RefreshTokenInfo, Long> {
    Optional<RefreshTokenInfo> findByMemberNoAndRefreshToken(Long memberNo, String refreshToken);
}
