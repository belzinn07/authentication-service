package com.belmironeto.authapi.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CadastroRequest {
    
    @NotBlank
    private String nome;

    @NotBlank
    private String email;
    @NotBlank

    @Size(min = 6, max = 30)
    private String senha;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
