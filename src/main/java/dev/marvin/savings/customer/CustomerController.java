package dev.marvin.savings.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/customers")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 10)
@RequiredArgsConstructor
@Tag(name = "Customer Resource", description = "CRUD REST APIs for Customer Management")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Customer", description = "Create customer is used to save customer in database",
            responses = {@ApiResponse(responseCode = "201", description = "201 Created")
            })
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
            log.info("Incoming request {}", registrationRequest);
            customerService.createCustomer(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping(value = "/{memberNumber}")
    public Customer getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }

    @PutMapping(value = "/{memberNumber}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("memberNumber") String memberNumber, @Valid @RequestBody CustomerUpdateRequest updateRequest, @RequestParam(value = "profileImage", required = false) MultipartFile multipartFile) {
        customerService.updateCustomer(memberNumber, updateRequest);
        return null;
    }

    @DeleteMapping("/{memberNumber}/delete/")
    public ResponseEntity<String> deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        customerService.deleteCustomer(memberNumber);
        return ResponseEntity.ok("customer deleted successfully");
    }

}


