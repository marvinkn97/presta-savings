package dev.marvin.savings.customer.controller;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/register")
    public String registerCustomer(@RequestBody @Valid CustomerRegistrationRequest registrationRequest) {
        return customerService.registerCustomer(registrationRequest);
    }

    @GetMapping(path = "/all", produces = {"application/json"})
    public List<CustomerResponse> getAllCustomers(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getSession().getId());
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{memberNumber}")
    public CustomerResponse getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }

    @PutMapping("/update/{memberNumber}")
    public String updateCustomer(@PathVariable("memberNumber") String memberNumber, @RequestBody CustomerUpdateRequest updateRequest) {
        return customerService.updateCustomer(memberNumber, updateRequest);
    }

    @DeleteMapping("/delete/{memberNumber}")
    public String deleteCustomer(@PathVariable("memberNumber") String memberNumber) {
        return customerService.deleteCustomer(memberNumber);
    }

}


