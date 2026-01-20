package com.belmironeto.authapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.belmironeto.authapi.user.domain.Usuario;
import com.belmironeto.authapi.user.repository.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;
    
    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                          .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole())
                .disabled(!usuario.isAtivo())
                .build();                  
    }
    
}
