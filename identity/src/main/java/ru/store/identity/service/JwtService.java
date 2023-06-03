package ru.store.identity.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.store.identity.config.SecurityConfig;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final SecurityConfig securityConfig;

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    public String generateToken(final String username) {
        return createToken(new HashMap<>(), username);
    }

    private String createToken(final Map<String, Object> claims, final String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + securityConfig.getConfig().getExpiration()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityConfig.getConfig().getSignKey()));
    }
}
