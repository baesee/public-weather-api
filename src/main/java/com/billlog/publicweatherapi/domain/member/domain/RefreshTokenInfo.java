package com.billlog.publicweatherapi.domain.member.domain;

import com.billlog.publicweatherapi.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name="idx_refreshTokenInfo_memberNo_refToken", columnList = "memberNo, refreshToken"))
public class RefreshTokenInfo extends BaseTimeEntity {
    /* 리프레시 토큰 정보 엔티티 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenNo;                    // 리프레시 토큰 번호
    @NotNull
    private Long memberNo;                         // 회원번호
    @NotNull
    private String refreshToken;                    // 리프레시 토큰
    @Builder
    public RefreshTokenInfo(Long refreshTokenNo, Long memberNo, String refreshToken) {
        this.refreshTokenNo = refreshTokenNo;
        this.memberNo = memberNo;
        this.refreshToken = refreshToken;
    }
}
