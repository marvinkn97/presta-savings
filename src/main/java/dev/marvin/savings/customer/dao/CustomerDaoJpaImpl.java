package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerDaoJpaImpl implements CustomerDao {

    @Override
    public void insertCustomer(Customer customer) {

    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerByMemberNumber(String memberNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Boolean updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Boolean deleteCustomer(Customer customer) {
        return null;
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return false;
    }
}
