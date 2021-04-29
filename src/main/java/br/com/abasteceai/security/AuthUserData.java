package br.com.abasteceai.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class AuthUserData implements AuthUser {

    private String username;
    private String password;
    private boolean active;
    private List<String> roles;
}