package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "jdbc")
public class CustomerJdbcDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerJdbcDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """
                SELECT id, name, email, mobile, government_id
                FROM tbl_customers
                """;
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(Integer customerId) {
        String sql = """
                SELECT id, name, email, mobile, government_id
                FROM tbl_customers
                WHERE id = ?
                """;
        return null;
    }

    @Override
    public Integer insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO (id, name, email, mobile, government_id)
                VALUES(?,?,?,?)
                """;
        return null;
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
