package dev.marvin.savings.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Boolean insertCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerByMemberNumber(String memberNumber);
    Optional<Customer> getCustomerByEmail(String email);
    Optional<Customer> getCustomerByName(String name);
    Boolean updateCustomer(Customer customer);
    Boolean deleteCustomer(Customer customer);
    Boolean existsCustomerWithEmail(String email);
}
