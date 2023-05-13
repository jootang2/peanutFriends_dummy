package com.example.peanutfriends_0505.utils;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {

    @Value("${jwt.secretKey}")
    public static String accessSecretKey;
    @Value("${jwt.refreshKey}")
    public static String refreshSecretKey;

    public final static Long accessTokenExpiredMs = 60 * 60 * 1000L; //1시간
    public final static Long refreshTokenExpiredMs = 7 * 24 * 60 * 60 * 1000L; //7일

    public static Long getMemberId(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("memberId", Long.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    } //만료 기한이 현재 시간보다 이전이면 isExpired : true

    public static String createAccessToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMemberId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredMs))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();
    }

    public static String createRefreshToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMemberId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiredMs))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();
    }
}
