package br.com.abasteceai.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AddressHandler {

    @Autowired
    private AddressService addressService;

    public Mono<ServerResponse> save(ServerRequest request) {

        Mono<AddressModel> address = request.bodyToMono(AddressModel.class);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(address.flatMap(addressService::save), AddressModel.class));
    }

    public Mono<ServerResponse> findByZip(ServerRequest request) {

        String zip = request.pathVariable("zip");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressService.findByZip(zip), AddressModel.class);
    }
}