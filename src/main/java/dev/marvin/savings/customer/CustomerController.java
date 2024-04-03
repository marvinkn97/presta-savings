package dev.marvin.savings.customer;

import dev.marvin.savings.advice.HttpResponse;
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
@Tag(name = "Customer Resource", description = "CRUD REST APIs for Customer Resource")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Customer", description = "Create customer is used to save customer in database",
            responses = {@ApiResponse(responseCode = "201", description = "201 Created")
    })
    @PreAuthorize(value = "hasAuthority('CUSTOMER_CREATE')")
    public ResponseEntity<HttpResponse> createCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
         customerService.createCustomer(registrationRequest);

         HttpResponse response = new HttpResponse(
                 HttpStatus.CREATED.value(),
                 HttpStatus.CREATED,
                 HttpStatus.CREATED.getReasonPhrase(),
                 "Customer registered successfully"
         );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping(value = "/{memberNumber}")
    public CustomerResponse getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }

    @PutMapping(value = "/update/{memberNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCustomer(@PathVariable("memberNumber") String memberNumber, @Valid @RequestBody CustomerUpdateRequest updateRequest, @RequestParam(value = "profileImage", required = false) MultipartFile multipartFile) {

        return customerService.updateCustomer(memberNumber, updateRequest);
    }

    @DeleteMapping("/delete/{memberNumber}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        String response = customerService.deleteCustomer(memberNumber);
        return ResponseEntity.ok(response);
    }

}


