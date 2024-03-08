package dev.marvin.savings.dao.impl;

import dev.marvin.savings.dao.CustomerDao;
import dev.marvin.savings.model.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Qualifier(value = "storedProcureImpl")
@RequiredArgsConstructor
public class CustomerDaoStoredProcedureImpl implements CustomerDao {

    private final SimpleJdbcCall simpleJdbcCall;

    @Override
    public void insertCustomer(Customer customer) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("status", "insert");
        mapSqlParameterSource.addValue("memberNumber", customer.getMemberNumber());

        simpleJdbcCall.withProcedureName("p_customer_screen").execute(mapSqlParameterSource);
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
