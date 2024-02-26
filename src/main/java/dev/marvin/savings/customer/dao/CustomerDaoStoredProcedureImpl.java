package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.model.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier(value = "stored procedures")
public class CustomerDaoStoredProcedureImpl implements CustomerDao{
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
