package dev.marvin.savings.appuser.customer;

import java.util.List;

public interface ICustomerService {
    String registerCustomer(CustomerRegistrationRequest registrationRequest);
    List<Customer> getAllCustomers();
    Customer getCustomerByMemberNumber(String memberNumber);
    Customer updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest);
    void deleteCustomer(String memberNumber);
}
