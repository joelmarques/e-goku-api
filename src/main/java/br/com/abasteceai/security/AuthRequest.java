package br.com.abasteceai.security;

import lombok.*;

@Getter@Setter
public class AuthRequest {
    private String username;
    private String password;
}