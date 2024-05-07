package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.auth.RegistrationRequest;

import java.util.List;

public interface ICustomerService {
    Customer registerCustomer(RegistrationRequest registrationRequest);
    boolean existCustomerWithEmail(String email);
    List<Customer> getAllCustomers();
    Customer getCustomerByMemberNumber(String memberNumber);
    Customer updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest);
    void deleteCustomer(String memberNumber);
}
