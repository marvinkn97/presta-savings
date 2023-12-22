package dev.marvin.savings.customer.controller;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerVO;
import dev.marvin.savings.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public Integer createCustomer(@RequestBody CustomerRegistrationRequest registrationRequest) {
        return customerService.insertCustomer(registrationRequest);
    }

    @GetMapping("/all")
    public List<CustomerVO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{memberNumber}")
    public CustomerVO getCustomerByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        return customerService.getCustomerByMemberNumber(memberNumber);
    }


}


