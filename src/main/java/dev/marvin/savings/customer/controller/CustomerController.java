package dev.marvin.savings.customer.controller;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
        Optional<CustomerResponse> customerResponse = customerService.getCustomerByMemberNumber(memberNumber);
        return customerResponse.orElse(null);
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


