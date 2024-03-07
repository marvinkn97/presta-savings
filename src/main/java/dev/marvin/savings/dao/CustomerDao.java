package dev.marvin.savings.dao;

import dev.marvin.savings.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    void insertCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerByMemberNumber(String memberNumber);

    Optional<Customer> getCustomerByEmail(String email);

    Boolean updateCustomer(Customer customer);

    Boolean deleteCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);
}
