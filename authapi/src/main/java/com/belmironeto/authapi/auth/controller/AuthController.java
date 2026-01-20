package com.belmironeto.authapi.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.belmironeto.authapi.auth.dto.AuthResponse;
import com.belmironeto.authapi.auth.dto.CadastroRequest;
import com.belmironeto.authapi.auth.dto.LoginRequest;
import com.belmironeto.authapi.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@Valid @RequestBody CadastroRequest request){
        authService.cadastrar(request.getNome(), request.getEmail(), request.getSenha());
    }

    @PostMapping("/login")
    public AuthResponse login (@Valid @RequestBody LoginRequest request){

        String token = authService.login(request.getEmail(), request.getSenha());
     
        return new AuthResponse(token);
    }

}
