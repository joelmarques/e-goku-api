package br.com.abasteceai.user;

import br.com.abasteceai.security.AuthUser;
import br.com.abasteceai.security.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class DefaultAuthUserService implements AuthUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<AuthUser> findByUsername(String username) {

        UserModel user = new UserModel();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("123"));
        user.setActive(true);
        user.setRoles(Arrays.asList("USER"));

        return Mono.just(user);
    }
}
