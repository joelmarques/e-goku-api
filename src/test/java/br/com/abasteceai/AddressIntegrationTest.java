package br.com.abasteceai;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import br.com.abasteceai.address.AddressModel;
import br.com.abasteceai.address.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EGokuApiApplication.class)
public class AddressIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    public void whenNoCredentials_thenIsUnauthorized() {
        this.testClient.get()
                .uri("/v1/addresses/*")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    public void givenAddress_whenGetAddressByZip_thenCorrectAddress() {

        AddressModel address = new AddressModel();
        address.setZip("04304000");

        given(addressRepository.findByZip("04304000")).willReturn(Mono.just(address));
        testClient.get()
                .uri("/v1/addresses/04304000")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AddressModel.class)
                .isEqualTo(address);

        verify(addressRepository).findByZip("04304000");
    }
}