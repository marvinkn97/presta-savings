package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Integer customerId);
    Integer insertCustomer(Customer customer);
    Boolean updateCustomer(Integer customerId);
    Boolean deleteCustomer(Integer customerId);
}
