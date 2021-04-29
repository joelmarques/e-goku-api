package br.com.abasteceai.user;

import io.swagger.v3.oas.annotations.Operation;
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
public class UserRouter {

    @RouterOperations({
            @RouterOperation(path = "/v1/users", operation = @Operation(operationId = "postUser", summary = "Grava um usuário no banco de dados", tags = { "Usuários" },
                    responses = { @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso!")}),
                    beanClass = DefaultUserService.class, beanMethod = "save")
    })
    @Bean
    public RouterFunction<ServerResponse> routeUser(UserHandler handler) {
        return RouterFunctions
                .route(POST("/v1/users").and(accept(MediaType.APPLICATION_JSON)), handler::save);
    }
}