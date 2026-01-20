package com.belmironeto.authapi.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements TokenService {

    @Value("${jwt.secret}")
    private String chaveSecreta;

    @Value("${jwt.expiration}")
    private String tempoDeExpiracao;

    @Override
    public String gerarToken(String email, String role) {

        long agora = System.currentTimeMillis();
        long expiracao = Long.parseLong(tempoDeExpiracao);

        Key key = Keys.hmacShaKeyFor(chaveSecreta.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date(agora))
                .setExpiration(new Date(agora + expiracao))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
