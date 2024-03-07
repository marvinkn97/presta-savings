package dev.marvin.savings.service.customer;

import dev.marvin.savings.dto.customer.CustomerRegistrationRequest;
import dev.marvin.savings.dto.customer.CustomerResponse;
import dev.marvin.savings.dto.customer.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {
    String registerCustomer(CustomerRegistrationRequest registrationRequest);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByMemberNumber(String memberNumber);
    String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
    String deleteCustomer(String memberNumber);
}
