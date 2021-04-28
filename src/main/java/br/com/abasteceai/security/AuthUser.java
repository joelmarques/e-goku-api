package br.com.abasteceai.security;

import java.util.List;

public interface AuthUser {

    String getUsername();
    String getPassword();
    boolean isActive();
    List<String> getRoles();
}