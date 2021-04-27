package br.com.abasteceai.address;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AddressRouter {

    @RouterOperations({
            @RouterOperation(path = "/v1/addresses", operation = @Operation(operationId = "postAddress", summary = "Grava um endereço no banco de dados", tags = { "Endereços" },
                    responses = { @ApiResponse(responseCode = "201", description = "Endereço salvo com sucesso!")}),
                    beanClass = DefaultAddressService.class, beanMethod = "save"),

            @RouterOperation(path = "/v1/addresses/{zip}", operation = @Operation(operationId = "getAddress", summary = "Busca um Endereço por CEP", tags = { "Endereços" },
                    responses = { @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!")},
                    parameters = { @Parameter(in = ParameterIn.PATH, name = "zip", description = "CEP do Endereço") }),
                    beanClass = DefaultAddressService.class, beanMethod = "findByZip")
    })
    @Bean
    public RouterFunction<ServerResponse> route(AddressHandler handler) {
        return RouterFunctions
                .route(POST("/v1/addresses").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(GET("/v1/addresses/{zip}").and(accept(MediaType.APPLICATION_JSON)), handler::findByZip);
    }
}