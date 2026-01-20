package com.belmironeto.authapi.user.service;

import com.belmironeto.authapi.user.domain.Usuario;

public interface UsuarioService {
    
    Usuario criarUsuario(String nome,String email, String senhaDescriptografada); 

}
