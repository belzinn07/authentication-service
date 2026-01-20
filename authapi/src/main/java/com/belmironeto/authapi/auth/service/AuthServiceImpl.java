package com.belmironeto.authapi.auth.service;

import org.springframework.stereotype.Service;

import com.belmironeto.authapi.user.service.UsuarioService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;

    public AuthServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void cadastrar(String nome, String email, String senha) {
        usuarioService.criarUsuario(nome, email, senha);
    }
    
}
