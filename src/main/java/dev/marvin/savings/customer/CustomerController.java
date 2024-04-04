package dev.marvin.savings.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/customers")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 10)
@RequiredArgsConstructor
@Tag(name = "Customer Resource", description = "CRUD REST APIs for Customer Management")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Customer", description = "Create customer is used to save customer in database",
            responses = {@ApiResponse(responseCode = "201", description = "201 Created")
            })
    @PreAuthorize(value = "hasAuthority('CUSTOMER_CREATE')")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        Customer customer = customerService.createCustomer(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping(value = "/{memberNumber}")
    public Customer getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }

    @PutMapping(value = "/update/{memberNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("memberNumber") String memberNumber, @Valid @RequestBody CustomerUpdateRequest updateRequest, @RequestParam(value = "profileImage", required = false) MultipartFile multipartFile) {
         customerService.updateCustomer(memberNumber, updateRequest);
         return null;
    }

    @DeleteMapping("/delete/{memberNumber}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        customerService.deleteCustomer(memberNumber);
        return ResponseEntity.ok("customer deleted successfully");
    }

}


