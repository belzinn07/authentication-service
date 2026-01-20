package com.belmironeto.authapi.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.belmironeto.authapi.user.domain.Usuario;
import com.belmironeto.authapi.user.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }


    @Override
    public Usuario criarUsuario(String nome, String email, String senhaDescriptografada) {
       
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");     
        }

        String senhaCriptografada = passwordEncoder.encode(senhaDescriptografada);
 
        Usuario novoUsuario = new Usuario(nome, email, senhaCriptografada, "USER" );
 
        return usuarioRepository.save(novoUsuario);

    } 
}
