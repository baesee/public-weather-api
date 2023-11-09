package com.billlog.publicweatherapi.domain.member.domain;

import com.billlog.publicweatherapi.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;
    @NotNull
    private String memberId;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String accountType;
    @NotNull
    private String policyAgree;
    @NotNull
    private String gender;

    @Builder
    public Member(Long memberNo, String memberId, String password, String nickname, String accountType, String policyAgree, String gender) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.password = password;
        this.nickname = nickname;
        this.accountType = accountType;
        this.policyAgree = policyAgree;
        this.gender = gender;
    }
    public void changePassword(String password){
        this.password = password;
    }
    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * 유저 권한 목록
     * 현 서비스(GAZI)에서는 권한은 딱히 없으므로 로그인 사용자는 USER로 세팅
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
