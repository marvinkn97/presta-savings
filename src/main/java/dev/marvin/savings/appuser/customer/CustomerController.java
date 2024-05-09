package dev.marvin.savings.appuser.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Resource", description = "Customer Management")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(value = "/register")
    @Operation(method = "POST", description = "Register Customer")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistrationRequest registrationRequest){
        var customer = customerService.registerCustomer(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('CSR')")
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


