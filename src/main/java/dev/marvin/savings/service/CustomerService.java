package dev.marvin.savings.service;

import dev.marvin.savings.model.dto.CustomerRegistrationRequest;
import dev.marvin.savings.model.dto.CustomerResponse;
import dev.marvin.savings.model.dto.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {
    String registerCustomer(CustomerRegistrationRequest registrationRequest);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByMemberNumber(String memberNumber);
    String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
    String deleteCustomer(String memberNumber);
}
