package com.belmironeto.authapi.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.belmironeto.authapi.security.TokenService;
import com.belmironeto.authapi.user.repository.UsuarioRepository;
import com.belmironeto.authapi.user.service.UsuarioService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private  final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
     

    public AuthServiceImpl(UsuarioService usuarioService,AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        
   
    }

    @Override
    public void cadastrar(String nome, String email, String senha) {
        usuarioService.criarUsuario(nome, email, senha);
    }

    @Override
    public String login(String email, String senha) {

         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );

        var usuario = usuarioRepository.findByEmail(email).orElseThrow();

        return tokenService.gerarToken(usuario.getEmail(), usuario.getRole());
                  
    }
    
}
