package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.entity.Customer;
import dev.marvin.savings.customer.rowmapper.CustomerRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
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
        Integer rowsAffected = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getMobile(), customer.getGovernmentId(), customer.getMemberNumber());
        log.info("CUSTOMER INSERT RESULT = " + rowsAffected);
        return rowsAffected;
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
    public void updateCustomer(Customer customer) {
        String sql = """
                UPDATE tlb_customers
                SET name = ?, email = ?, mobile = ?
                WHERE member_number = ?
                """;
        Integer rowsAffected = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getMobile(), customer.getMemberNumber());
        log.info("[%s] Customer UPDATE RESULT = ".formatted(rowsAffected));
    }

    @Override
    public void deleteCustomer(String memberNumber) {
        String sql = """
                DELETE FROM tlb_customers
                WHERE member_number = ?
                """;
        Integer rowsAffected = jdbcTemplate.update(sql, memberNumber);
        log.info("[%s] Customer deleted successfully".formatted(rowsAffected));
    }
}
