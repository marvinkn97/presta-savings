package dev.marvin.savings.journey;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.service.JwtService;
import dev.marvin.savings.dto.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class AppUserIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    JwtService jwtService;

    @Test
    void givenListOfAppUsers_whenGetAllAppUsers_thenReturnListOfAppUsers(){

        appUserRepository.deleteAll();

        //given
        var admin = AppUser.builder()
                .username("admin@presta")
                .password("password")
                .role(Role.ADMIN)
                .build();

        var csr = AppUser.builder()
                .username("csr@presta")
                .password("password")
                .role(Role.CSR)
                .build();

        var customer = AppUser.builder()
                .username("customer@presta")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

        var users = List.of(admin, csr, customer);
        appUserRepository.saveAll(users);

        var authentication = new UsernamePasswordAuthenticationToken(
                admin.getUsername(), admin.getPassword(), admin.getAuthorities()
        );

        var token = jwtService.generateJwtToken(authentication);

         var responseBody = webTestClient.get().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AppResponse.class)
                .returnResult()
                .getResponseBody();

        log.info("********************* {}", responseBody);

        @SuppressWarnings("unchecked")
        var data = (List<AppUser>) Objects.requireNonNull(responseBody).data();

        // Verify the response
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.status()).isEqualTo(HttpStatus.OK.value());
        assertThat(data).hasSize(3);
    }
}
