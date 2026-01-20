package com.belmironeto.authapi.user.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
    }
)

public class Usuario {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, length = 150)
   private String nome;

   @Column(nullable = false, length = 150, unique = true)
   private String email;

   @Column(nullable = false, length = 255)
   private String senha;

   @Column(nullable = false, length = 50)
   private String role;

   @Column(nullable = false)
   private boolean ativo = true;

   @Column(name = "criado_em",nullable = false )
   private LocalDateTime criadoEm;

   protected Usuario(){

   }


   public Usuario(String nome, String email, String senha, String role) {
     this.nome = nome;
     this.email = email;
     this.senha = senha;
     this.role = role;
     this.ativo = true;
     this.criadoEm = LocalDateTime.now();
   }

   public Long getId() {
     return id;
   }
   
   public String getNome() {
     return nome;
   }
   
   public String getEmail() {
     return email;
   }
   
   public String getSenha() {
     return senha;
   }
    
   public String getRole() {
     return role;
   }
   
   public boolean isAtivo() {
     return ativo;
   }

   public LocalDateTime getCriadoEm() {
     return criadoEm;
   }

}
