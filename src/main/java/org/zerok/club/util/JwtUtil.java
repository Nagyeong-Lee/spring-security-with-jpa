package org.zerok.club.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private final String jwtKey;

    public JwtUtil(@Value("${jwt.key}") String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String generateToken(String email) {
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("typ", "JWT");
        jwtHeader.put("alg", "HS256");

        Map<String, Object> jwtPayload = new HashMap<>();
        jwtPayload.put("email", email);

        Long expiredTime = 1000 * 60L * 60L * 24L * 3L; // 7일
        Date date = new Date();
        date.setTime(date.getTime() + expiredTime);

        return Jwts.builder()
                .setHeader(jwtHeader)
                .setClaims(jwtPayload)
                .setIssuedAt(new Date())
                .setExpiration(date)
                .signWith(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailByJwtToken(String jwtToken){
        return (String) Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("email");
    }

}
