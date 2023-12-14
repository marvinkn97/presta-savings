package dev.marvin.savings.customer.service;

import dev.marvin.savings.customer.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Integer customerId);
}
