package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.exception.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Resource", description = "Customer Management")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registration")
    @Operation(method = "POST", description = "Register Customer")
    public ResponseEntity<ServerResponse> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest){
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

    @PostMapping(value = "/registration/confirm")
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
    @PreAuthorize(value = "hasAuthority('CSR')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping(value = "/{memberNumber}")
    public CustomerResponse getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }

    @PutMapping(value = "/{memberNumber}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("memberNumber") String memberNumber, @Valid @RequestBody CustomerUpdateRequest updateRequest, @RequestParam(value = "profileImage", required = false) MultipartFile multipartFile) {
        customerService.updateCustomer(memberNumber, updateRequest);
        return null;
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


