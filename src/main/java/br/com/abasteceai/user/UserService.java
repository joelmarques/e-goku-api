package br.com.abasteceai.user;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserModel> save(UserModel user);
    Mono<UserModel> findByUsername(String username);
}