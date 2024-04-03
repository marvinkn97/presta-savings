package dev.marvin.savings.customer;

import java.util.List;

public interface CustomerService {
    void createCustomer(CustomerRegistrationRequest registrationRequest);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByMemberNumber(String memberNumber);
    String updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
    String deleteCustomer(String memberNumber);
}
