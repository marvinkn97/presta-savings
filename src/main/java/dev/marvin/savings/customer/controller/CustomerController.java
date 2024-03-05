package dev.marvin.savings.customer.controller;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.service.CustomerService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/customers")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 10)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        String response = customerService.registerCustomer(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customers = customerService.getAllCustomers();
        try {
            //trigger loader for client side
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping(value = "/{memberNumber}")
    public CustomerResponse getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        Optional<CustomerResponse> customerResponse = customerService.getCustomerByMemberNumber(memberNumber);
        return customerResponse.orElse(null);
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


