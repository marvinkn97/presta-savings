package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.model.Customer;

import java.util.List;

public interface CustomerDao {
    void insertCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerByMemberNumber(String memberNumber);

    Customer getCustomerByEmail(String email);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);
}
