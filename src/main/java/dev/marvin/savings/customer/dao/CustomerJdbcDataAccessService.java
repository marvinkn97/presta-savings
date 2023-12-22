package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.entity.Customer;
import dev.marvin.savings.customer.rowmapper.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository(value = "jdbc")
public class CustomerJdbcDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    @Autowired
    public CustomerJdbcDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public Integer insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO tlb_customers (name, email, mobile, government_id, member_number)
                VALUES(?,?,?,?,?)
                """;
        return jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getMobile(), customer.getGovernmentId(), customer.getMemberNumber());
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """
                SELECT name, email, mobile, government_id, member_number
                FROM tlb_customers
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Customer getCustomerByMemberNumber(String memberNumber) {
        String sql = """
                SELECT name, email, mobile, government_id, member_number
                FROM tlb_customers
                WHERE member_number = ?
                """;
        return jdbcTemplate.queryForObject(sql, customerRowMapper, memberNumber);
    }

    @Override
    public Integer updateCustomer(String memberNumber, Customer customer) {
        String sql = """
                UPDATE tlb_customers
                SET name = ?, email = ?, mobile = ?
                WHERE member_number = ?
                """;
         return jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getMobile(), customer.getMemberNumber());
    }
}
