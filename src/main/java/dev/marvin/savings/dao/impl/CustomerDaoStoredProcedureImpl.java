package dev.marvin.savings.dao.impl;

import dev.marvin.savings.dao.CustomerDao;
import dev.marvin.savings.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerDaoStoredProcedureImpl implements CustomerDao {
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
