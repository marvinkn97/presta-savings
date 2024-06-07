package dev.marvin.savings.appuser.customer;

import java.util.List;

public interface CustomerService {
    String registerCustomer(CustomerRegistrationRequest registrationRequest);
    void confirmEmailToken(String token);
    void refreshConfirmationToken(String email);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByMemberNumber(String memberNumber);
    void updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest);
    void deleteCustomer(String memberNumber);
}
