package br.com.abasteceai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EGokuApiApplication.class)
public class UserIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void whenNoCredentials_thenIsUnauthorized() {
        this.testClient.get()
                .uri("/v1/users/*")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}