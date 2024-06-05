package dev.marvin.savings.journey;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.appuser.customer.CustomerRegistrationRequest;
import dev.marvin.savings.appuser.customer.CustomerUpdateRequest;
import dev.marvin.savings.auth.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j

public class CustomerIntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void customerAPIJourneyTest() throws Exception {
        var registrationRequest = new CustomerRegistrationRequest("foobar", "Foo Bar", "foo@example.com", "password", "254792876354", "339872634", "A2323E43534C");

        appUserRepository.deleteAll();

        // test customer registration
        webTestClient.post().uri("/api/v1/customers/registration")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(registrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();

        var csr = AppUser.builder()
                .username("csr@presta")
                .password("password")
                .role(Role.CSR)
                .build();

        appUserRepository.save(csr);

        var authentication = new UsernamePasswordAuthenticationToken(
                csr.getUsername(), csr.getPassword(), csr.getAuthorities()
        );

        var token = jwtService.generateJwtToken(authentication);

        // test getAllCustomers
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].username", CoreMatchers.is(registrationRequest.username())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email", CoreMatchers.is(registrationRequest.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].mobileNumber", CoreMatchers.is(registrationRequest.mobileNumber())))
                .andReturn()
                .getResponse()
                .getContentAsString();


        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        var memberNumber = jsonArray.getJSONObject(0).get("memberNumber");


        // test getCustomerByID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + memberNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username", CoreMatchers.is(registrationRequest.username())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", CoreMatchers.is(registrationRequest.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobileNumber", CoreMatchers.is(registrationRequest.mobileNumber())));

        var customerAuthentication = new UsernamePasswordAuthenticationToken(
                registrationRequest.username(), registrationRequest.password(), Collections.singletonList(new SimpleGrantedAuthority("CUSTOMER"))
        );

        var update = new CustomerUpdateRequest(null, null, null, "254792345600");

        var customerToken = jwtService.generateJwtToken(customerAuthentication);

        // test updateCustomer
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/" + memberNumber)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + customerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());


        // test if customer mobile number has been updated
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + memberNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobileNumber", CoreMatchers.is(update.mobileNumber())));

        //test deleteCustomer
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/" + memberNumber)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + customerToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        // test customer has been soft-deleted
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + memberNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.isDeleted", CoreMatchers.is(true)));

    }
}
