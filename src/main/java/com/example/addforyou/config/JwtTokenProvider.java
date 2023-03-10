package com.example.addforyou.config;

import com.example.addforyou.dto.Token;
import com.example.addforyou.exception.member.UnauthorizedMemberException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMilliseconds;

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    private String createToken(String payload, long expiredLength) { // 순수 토큰 생성
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + expiredLength))
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compact();
    }

    private Token createAccessToken(String payload) { // Access Token 생성
        String token = createToken(payload, accessTokenValidityInMilliseconds);

        return Token.builder()
                .token(token)
                .expiredTime(accessTokenValidityInMilliseconds)
                .build();
    }

    private Token createRefreshToken(String payload) { // Refresh Token 생성
        String token = createToken(UUID.randomUUID().toString(), accessTokenValidityInMilliseconds);

        return Token.builder()
                .token(token)
                .expiredTime(accessTokenValidityInMilliseconds)
                .build();

    }

    public String getPayload(String token) { // PayLoad 정보 반환하는 메소드
        try {
            return Jwts.parser()
                       .setSigningKey(secretKey)
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new UnauthorizedMemberException("로그인이 필요합니다.");
        }
    }

    public boolean validateToken(String token) { // token 의 유효성 검증하는 메소드
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
