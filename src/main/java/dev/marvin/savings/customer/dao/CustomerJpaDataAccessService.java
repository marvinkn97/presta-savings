package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository(value = "jpa")
public class CustomerJpaDataAccessService implements CustomerDao {

    @Override
    public List<Customer> getAllCustomers() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Customer> getCustomerById(Integer customerId) {
        return Optional.empty();
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
