package com.belmironeto.authapi.security;

public interface TokenService {

    String gerarToken(String email, String role);
   
}