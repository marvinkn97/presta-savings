package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer customerId);
    Integer insertCustomer(Customer customer);
    Boolean updateCustomer(Integer customerId);
    Boolean deleteCustomer(Integer customerId);
}
