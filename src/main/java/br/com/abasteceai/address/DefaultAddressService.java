package br.com.abasteceai.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultAddressService implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Mono<AddressModel> save(AddressModel address) { return addressRepository.save(address); }

    @Override
    @Cacheable(value = "addresses", key = "#zip")
    public Mono<AddressModel> findByZip(String zip) { return addressRepository.findByZip(zip); }
}