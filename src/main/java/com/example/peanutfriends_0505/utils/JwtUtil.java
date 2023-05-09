package com.example.peanutfriends_0505.utils;

import com.example.peanutfriends_0505.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String createJwt(Member member, String secretKey, Long expiredMs){
        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMemberId());
        claims.put("memberName", member.getName());
        claims.put("memberEmail", member.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
