package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Integer insertCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerByMemberNumber(String memberNumber);
}
