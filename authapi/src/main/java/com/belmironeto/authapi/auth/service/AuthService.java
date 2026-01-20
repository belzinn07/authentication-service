package com.belmironeto.authapi.auth.service;

public interface AuthService{

    void cadastrar(String nome, String email, String senha);
    String login(String email, String senha);

}