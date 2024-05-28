package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.config.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Resource", description = "Customer Management")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registration")
    @Operation(method = "POST", description = "Register Customer")
    public ResponseEntity<ServerResponse> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        log.info("Registration Request: {}", registrationRequest);
        var response = customerService.registerCustomer(registrationRequest);
        log.info("Response: {}", response);

        ServerResponse serverResponse = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.value())
                .reason(HttpStatus.CREATED.getReasonPhrase())
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(serverResponse);
    }

    @PostMapping("/registration/confirm")
    public ResponseEntity<ServerResponse> confirmEmailToken(@RequestParam(name = "token") String token) {
        customerService.confirmEmailToken(token);

        ServerResponse serverResponse = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("Email Confirmed Successfully")
                .build();

        return ResponseEntity.ok(serverResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CSR')")
    public ResponseEntity<ServerResponse> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();

        ServerResponse response = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(customers)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberNumber}")
    public ResponseEntity<ServerResponse> getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        var customer = customerService.getCustomerByMemberNumber(memberNumber);
        ServerResponse response = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{memberNumber}")
    public ResponseEntity<ServerResponse> updateCustomer(@PathVariable("memberNumber") String memberNumber, @Valid @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(memberNumber, updateRequest);

        ServerResponse response = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("customer updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memberNumber}")
    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    public ResponseEntity<ServerResponse> deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        customerService.deleteCustomer(memberNumber);

        ServerResponse response = ServerResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .reason(HttpStatus.OK.getReasonPhrase())
                .data("customer deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

}


