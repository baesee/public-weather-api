package com.billlog.publicweatherapi.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.billlog.publicweatherapi.domain.member.domain.Member;
import com.billlog.publicweatherapi.global.custom.exception.BLBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider{
    private final Key key;
    private final Long accessTokenValidMinute;
    private final Long refreshTokenValidDay;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expire.minute}") int accessTokenValidMin, @Value("${jwt.expire.day}") int refreshTokenValidDay) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidMinute = accessTokenValidMin * 60 * 1000L;             // 엑세스 토큰 만료기간 세팅
        this.refreshTokenValidDay = ( refreshTokenValidDay * 1440 ) * 60 * 1000L;   // 리프레시 토큰 만료기간 세팅
    }

    public JwtTokenInfoVo generateToken(Authentication authentication , Member memberInfo) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        // Access Token 생성
        String accessToken = Jwts.builder()
                /* TODO : 회원 Entity 생성후 작업
                .setSubject(accountInfo.getMemberId())

                .claim("roles", authorities)
                .claim("userNo", accountInfo.getMemberInfo().getUserNo())
                .claim("nickname", accountInfo.getMemberInfo().getNickname())
                .claim("accountNo", accountInfo.getAccountNo())
                */
                .claim("memberNo", memberInfo.getMemberNo())
                .claim("nickName", memberInfo.getNickname())
                .claim("roles", authorities)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidMinute))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidDay))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtTokenInfoVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("roles") == null) {
            throw new BLBusinessException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        TokenPayloadDto payload = TokenPayloadDto.builder()
                .memberId(claims.getSubject())
                .memberNo(claims.get("memberNo").toString())
                .build();

        // UserDetails 객체를 만들어서 Authentication 리턴
        //UserDetails principal = new User(claims.getSubject(), "", authorities);
        //return new UsernamePasswordAuthenticationToken(principal, "", authorities);

        return new UsernamePasswordAuthenticationToken(payload, "", authorities);
    }

    // 토큰의 유효성 체크
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Request Header 에서 Access 토큰 정보 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Request Header 에서 Refresh 토큰 정보 추출
    public String resolveRefreshToken(HttpServletRequest request) {
        // SO Simple Veersion
        // request.getHeader("X-AUTH-REFRESH-TOKEN");
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


