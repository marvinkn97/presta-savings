package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.dao.rowmapper.CustomerRowMapper;
import dev.marvin.savings.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRowMapper customerRowMapper;

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO t_customer (member_number, email, password, created_date, role)
                VALUES(?, ?, ?, ?, ?)
                """;
        int rowsAffected = jdbcTemplate.update(sql, customer.getMemberNumber(), customer.getEmail(), customer.getPassword(), customer.getCreatedDate(), customer.getRole().name());
        log.info("CUSTOMER INSERT RESULT = " + rowsAffected);
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Customer getCustomerByMemberNumber(String memberNumber) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE member_number = ?
                """;
        return jdbcTemplate.queryForObject(sql, customerRowMapper, memberNumber);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE email = ?
                """;
        return jdbcTemplate.queryForObject(sql, customerRowMapper);
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = """
                UPDATE t_customer
                SET customer_name = ?, email = ?, mobile_no = ?
                WHERE member_number = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getMobile(), customer.getMemberNumber());
        log.info("CUSTOMER UPDATE RESULT = " + rowsAffected);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        final String sql = """
                DELETE FROM t_customer
                WHERE member_number = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql, customer.getMemberNumber());
        log.info("CUSTOMER DELETE RESULT = " + rowsAffected);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        final String sql = "SELECT COUNT(*) FROM t_customer WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
