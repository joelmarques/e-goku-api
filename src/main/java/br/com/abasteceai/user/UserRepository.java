package br.com.abasteceai.user;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveSortingRepository<UserModel, String> {

    Mono<UserModel> findByUsername(String username);
}