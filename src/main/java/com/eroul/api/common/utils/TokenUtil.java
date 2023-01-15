package com.eroul.api.common.utils;

import com.eroul.api.common.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenUtil {
    private final static String CLAIMS_AUTH_KEY = "auth";

    private final Key secretKey;
    private final long validMs;

    public TokenUtil(@Value("${jwt.secret}") String secretKey,
                     @Value("${jwt.token-valid}") long validMs) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validMs = validMs;
    }

    public TokenDto generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiredIn = new Date(now.getTime() + validMs);

        String accessToken = Jwts.builder()
                                .setSubject(authentication.getName())
                                .claim("auth", getStringAuthority((List<GrantedAuthority>) authentication.getAuthorities()))
                                .setIssuedAt(now)
                                .setExpiration(expiredIn)
                                .signWith(secretKey, SignatureAlgorithm.HS256)
                                .compact();
        return new TokenDto(accessToken);
    }

    public boolean validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken);
            return true;
        } catch (Exception e) {
            log.error(" token invalid : {}", accessToken);
        }
        return false;
    }


    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        List<GrantedAuthority> grantedAuthorities = parseStringAuthority(claims.get("auth").toString());
        // username, authority만 생성
        UserDetails principal = new User(claims.getSubject(), StringUtils.EMPTY, grantedAuthorities);

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    private Claims parseClaims(String accessToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    private String getStringAuthority(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private List<GrantedAuthority> parseStringAuthority(String authority) {
        return Arrays.stream(authority.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
