package br.com.abasteceai.user;

import br.com.abasteceai.security.AuthUser;
import br.com.abasteceai.security.AuthUserData;
import br.com.abasteceai.security.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class DefaultAuthUserService implements AuthUserService {

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserService userService;

    @Override
    public Mono<AuthUser> findByUsername(String username) {

        return userService.findByUsername(username).flatMap(this::convert);
    }

    private Mono<AuthUser> convert(UserModel userModel) {

        AuthUserData userData = new AuthUserData();
        userData.setUsername(userModel.getUsername());
        userData.setPassword(userModel.getPassword());
        userData.setActive(userModel.isActive());
        userData.setRoles(userModel.getRoles().stream().map(r -> ROLE_PREFIX + r).collect(Collectors.toList()));

        return Mono.just(userData);
    }
}
