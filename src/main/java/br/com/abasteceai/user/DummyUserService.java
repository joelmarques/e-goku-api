package br.com.abasteceai.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DummyUserService implements CommandLineRunner {

    private static final String ADMIN = "admin";

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        userService.findByUsername(ADMIN).switchIfEmpty(userService.save(createAdminUser())).subscribe();
    }

    private UserModel createAdminUser() {

        UserModel user = new UserModel();
        user.setUsername(ADMIN);
        user.setPassword(ADMIN);
        user.setActive(true);
        user.setRoles(Arrays.asList(ADMIN.toUpperCase()));

        return user;
    }
}