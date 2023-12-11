package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.Customer;
import dev.marvin.savings.customer.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerJpaDataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerJpaDataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Integer insertCustomer(Customer customer) {
        return 0;
    }

    @Override
    public Boolean updateCustomer(Integer customerId) {
        return false;
    }

    @Override
    public Boolean deleteCustomer(Integer customerId) {
        return false;
    }
}
