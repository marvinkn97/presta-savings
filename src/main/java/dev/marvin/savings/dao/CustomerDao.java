package dev.marvin.savings.dao;

import dev.marvin.savings.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    void insertCustomer(Customer customer);
    List<Customer> getAllCustomers(int pageNumber, int pageSize);
    Optional<Customer> getCustomerByMemberNumber(String memberNumber);
    Optional<Customer> getCustomerByEmail(String email);
    Optional<Customer> getCustomerByName(String name);
    Boolean updateCustomer(Customer customer);
    Boolean deleteCustomer(Customer customer);
    Boolean existsCustomerWithEmail(String email);
}
