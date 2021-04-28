package br.com.abasteceai.user;

import br.com.abasteceai.security.AuthUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class UserModel implements AuthUser {

    private String username;
    private String password;
    private boolean active;
    private List<String> roles;
}