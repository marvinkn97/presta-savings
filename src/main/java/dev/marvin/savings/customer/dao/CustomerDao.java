package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.model.Customer;

import java.util.List;

public interface CustomerDao {
    void insertCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerByMemberNumber(String memberNumber);
    void updateCustomer(Customer customer);
    void deleteCustomer(String memberNumber);
    boolean existsCustomerWithEmail(String email);
}
