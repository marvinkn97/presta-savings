package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    String registerCustomer(CustomerRegistrationRequest registrationRequest);
    List<CustomerResponse> getAllCustomers();
    Optional<CustomerResponse> getCustomerByMemberNumber(String memberNumber);
    String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
    String deleteCustomer(String memberNumber);
}
