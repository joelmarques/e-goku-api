package br.com.abasteceai.address;

import reactor.core.publisher.Mono;

public interface AddressService {

    Mono<AddressModel> save(AddressModel address);
    Mono<AddressModel> findByZip(String zip);
}