package br.com.abasteceai.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("e-Goku API")
                        .description(getDescription())
                        .version("Versão 1")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("https://www.abasteceai.com.br/")
                        .url("https://www.abasteceai.com.br/"));
    }

    private String getDescription() {

        StringBuilder description = new StringBuilder();
        description.append("A loja Goku é um e-commerce de vendas de produtos alimentícios que está crescendo 20% ao ano.\n\n");
        description.append("E para ajudar nesse crescimento foi construída uma api que mantêm íntegra as informações de endereço de entrega de seus clientes.");

        return description.toString();
    }
}
