package dev.marvin.savings.customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerRegistrationRequest registrationRequest);
    List<Customer> getAllCustomers();
    Customer getCustomerByMemberNumber(String memberNumber);
    Customer updateCustomer(String memberNumber, CustomerUpdateRequest customerUpdateRequest);
    void deleteCustomer(String memberNumber);
}
