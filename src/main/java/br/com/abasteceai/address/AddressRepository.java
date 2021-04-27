package br.com.abasteceai.address;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface AddressRepository extends ReactiveSortingRepository<AddressModel, String> {

    Mono<AddressModel> findByZip(String zip);
}