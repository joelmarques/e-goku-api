package br.com.abasteceai.address;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AddressRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AddressHandler handler) {
        return RouterFunctions
                .route(POST("/v1/addresses").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(GET("/v1/addresses/{zip}").and(accept(MediaType.APPLICATION_JSON)), handler::findByZip);
    }
}