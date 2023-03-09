package com.example.addforyou.config;

import com.example.addforyou.dto.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.UUID;

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
}
