package dev.marvin.savings.journey;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.auth.jwt.JwtService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AppUserIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();
    }

    @Test
    void givenListOfAppUsers_whenGetAllAppUsers_thenReturnListOfAppUsers() throws Exception {

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
                admin.getUsername(),admin.getPassword(), admin.getAuthorities()
        );

        var token = jwtService.generateJwtToken(authentication);

        webTestClient.get().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus()
                .isOk();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", CoreMatchers.is("admin@presta")));

    }


}
