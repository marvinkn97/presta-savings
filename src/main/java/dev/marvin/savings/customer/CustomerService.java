package dev.marvin.savings.customer;

import java.util.List;

public interface CustomerService {
    void createCustomer(CustomerRegistrationRequest registrationRequest);
    List<Customer> getAllCustomers();
    Customer getCustomerByMemberNumber(String memberNumber);
    Customer updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest);
    void deleteCustomer(String memberNumber);
}
