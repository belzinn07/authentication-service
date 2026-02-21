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

    public boolean tokenValido(String token){//método para validar o token JWT, verificando se ele é válido e não expirou
        //try-catch para lidar com possíveis exceções durante a validação do token JWT, como token inválido ou expirado
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extrairEmail(String token) {//método para extrair o email do token JWT, retornando o email contido no campo "sub" do token
        return getClaims(token);
    }

   private String getClaims(String token){//método privado para extrair as reivindicações (claims) do token JWT, retornando o valor do campo "sub" que contém o email do usuário
   //em outras palavras, esse método é responsável por decodificar o token JWT e extrair o email do usuário para que possa ser usado na autenticação e autorização das requisições HTTP
      return Jwts.parserBuilder()
                .setSigningKey(chaveSecreta.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
   }
}
