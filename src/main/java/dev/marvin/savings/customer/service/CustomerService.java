package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.dto.CustomerRegistrationRequest;
import dev.marvin.savings.customer.dto.CustomerUpdateRequest;
import dev.marvin.savings.customer.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    String insertCustomer(CustomerRegistrationRequest registrationRequest);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByMemberNumber(String memberNumber);
    String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
    String deleteCustomer(String memberNumber);
}
