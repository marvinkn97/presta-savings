package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    Integer insertCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Integer customerId);

    Boolean updateCustomer(Integer customerId);

    Boolean deleteCustomer(Integer customerId);
}
