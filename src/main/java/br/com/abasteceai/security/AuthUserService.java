package br.com.abasteceai.security;

import reactor.core.publisher.Mono;

public interface AuthUserService {

    Mono<AuthUser> findByUsername(String username);
}