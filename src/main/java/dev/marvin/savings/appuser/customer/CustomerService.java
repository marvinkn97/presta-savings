package dev.marvin.savings.appuser.customer;

import java.util.List;

public interface CustomerService {

    void saveCustomer(Customer customer);
    boolean existCustomerWithEmail(String email);

    List<Customer> getAllCustomers();
    Customer getCustomerByMemberNumber(String memberNumber);
    Customer updateCustomer(String memberNumber, CustomerUpdateRequest updateRequest);
    void deleteCustomer(String memberNumber);
}
