package dev.marvin.savings.controller;

import dev.marvin.savings.customer.CustomerRegistrationRequest;
import dev.marvin.savings.model.dto.CustomerRegistrationResponse;
import dev.marvin.savings.model.dto.CustomerResponse;
import dev.marvin.savings.customer.CustomerUpdateRequest;
import dev.marvin.savings.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/customers")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 10)
@RequiredArgsConstructor
@Tag(name = "Customer Resource", description = "CRUD REST APIs for Customer Resource")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "Customer registered successfully")
    @Operation(summary = "Register Customer", description = "Register customer is used to save customer in database")
    public ResponseEntity<CustomerRegistrationResponse> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        String response = customerService.registerCustomer(registrationRequest);
        CustomerRegistrationResponse registrationResponse = new CustomerRegistrationResponse(LocalDateTime.now(), HttpStatus.CREATED.toString(), response);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
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

        System.out.println(memberNumber);
//        String fileName = multipartFile.getName();
//        System.out.println(fileName);

        return customerService.updateCustomer(memberNumber, updateRequest);
    }

    @DeleteMapping("/delete/{memberNumber}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        String response = customerService.deleteCustomer(memberNumber);
        return ResponseEntity.ok(response);
    }

}


