package com.intern_project.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // 비밀키는 환경 변수나 설정 파일에서 가져오는 것이 좋습니다.
    private String secret = "valnaslnalsknlnsalkvnlkanslkndlrknwlnalsknaSSADVAVASZXCVSAADFvalnaslnalsknlnsalkvnlkanslkndlrknwlnalsknaSSADVAVASZXCVSAADF";
    private long expiration = 1000 * 60 * 60; // 1시간

    // 이메일을 담은 JWT 토큰 생성
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // JWT 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // JWT 토큰에서 클레임을 추출
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰의 유효성 검사 (만료 여부)
    public boolean isTokenExpired(String token) {
        Date expirationDate = getClaimsFromToken(token).getExpiration();
        return expirationDate.before(new Date());
    }

    // JWT 토큰의 유효성 검사 (이메일과 만료 여부)
    public boolean validateToken(String token, String email) {
        String tokenEmail = getEmailFromToken(token);
        return tokenEmail.equals(email) && !isTokenExpired(token);
    }
}
