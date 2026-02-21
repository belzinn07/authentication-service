package com.belmironeto.authapi.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;//classe de filtro que garante que o filtro seja executado apenas uma vez por requisição, evitando múltiplas execuções desnecessárias

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component// @Component para que o Spring possa gerenciar a classe e injetá-la onde for necessário 
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;//injeção de dependência do JwtService para validar os tokens JWT
    private final UsuarioDetailsServiceImpl usuarioDetailsService;//injeção de dependência do UsuarioDetailsServiceImpl para carregar os detalhes do usuário com base no token JWT


    public JwtFilter(JwtService jwtService, UsuarioDetailsServiceImpl usuarioDetailsService) {
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)//método do filtro que intercepta as requisições HTTP, extrai o token JWT do cabeçalho Authorization, valida o token e, se for válido, carrega os detalhes do usuário e configura a autenticação no contexto de segurança do Spring
            throws ServletException, IOException {//throws ServletException, IOException para lidar com possíveis exceções durante o processo de filtragem das requisições HTTP
       
            String token = extrairToken(request);//método para extrair o token JWT do cabeçalho Authorization da requisição HTTP

            if (token != null && jwtService.tokenValido(token)){
                
                String email = jwtService.extrairEmail(token);//método para extrair o email do token JWT usando o JwtService
                var usuarioDetails = usuarioDetailsService.loadUserByUsername(email);//método para carregar os detalhes do usuário com base no email extraído do token JWT usando o UsuarioDetailsServiceImpl
                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuarioDetails, null, usuarioDetails.getAuthorities());//criação de um objeto de autenticação do Spring Security usando os detalhes do usuário carregados
                authentication.setDetails(usuarioDetails);//configuração dos detalhes da autenticação com os detalhes do usuário

                SecurityContextHolder.getContext().setAuthentication(authentication);//configuração da autenticação no contexto de segurança do Spring para que o usuário seja autenticado durante a requisição

            }

            filterChain.doFilter(request, response);//continuação da cadeia de filtros para processar a requisição HTTP
            
    }

    private String extrairToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");//método para extrair o token JWT do cabeçalho Authorization da requisição HTTP
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);//remover o prefixo "Bearer " do token JWT
        }
        return null;//retorna null se o token JWT não estiver presente ou não for válido
    }
    
}
