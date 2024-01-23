package dev.marvin.savings.customer.controller;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public String createCustomer(@RequestBody CustomerRegistrationRequest registrationRequest) {
        return customerService.insertCustomer(registrationRequest);
    }

    @GetMapping("/all")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{memberNumber}")
    public CustomerResponse getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }

    @PutMapping("/update/{memberNumber}")
    public String updateCustomer(@PathVariable("memberNumber") String memberNumber, @RequestBody CustomerUpdateRequest updateRequest){
        return customerService.updateCustomer(memberNumber, updateRequest);
    }
    @DeleteMapping("/delete/{memberNumber}")
    public String deleteCustomer(@PathVariable("memberNumber") String memberNumber){
        return customerService.deleteCustomer(memberNumber);
    }

}


