package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository(value = "jdbc")
@Primary
public class CustomerDaoJdbcImpl implements CustomerDao {

    //Look into NamedParameterJdbcTemplate

    private final JdbcTemplate jdbcTemplate;

    private final CustomerRowMapper customerRowMapper;

    public CustomerDaoJdbcImpl(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO t_customer (member_number, customer_name, email, password, created_date)
                VALUES(?, ?, ?, ?, ?)
                """;
        int rowsAffected = jdbcTemplate.update(sql, customer.getMemberNumber(), customer.getName(), customer.getEmail(), customer.getPassword(), customer.getCreatedDate());
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
    public Optional<Customer> getCustomerByMemberNumber(String memberNumber) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE member_number = ?
                """;

        /*
        The queryForObject method will throw an org.springframework.dao.EmptyResultDataAccessException if
        no rows are returned by the query.
        Customer c =jdbcTemplate.queryForObject(sql, customerRowMapper, memberNumber);
         */

        // handles the case where no rows are returned by the query without throwing an exception.
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper, memberNumber);
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE email = ?
                """;
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper, email);
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    @Override
    public Boolean updateCustomer(Customer customer) {
        int rowsAffected = 0;

        if (customer.getName() != null) {
            String sql = """
                    UPDATE t_customer
                    SET customer_name = ?, updated_date = ?
                    WHERE member_number = ?
                    """;
            rowsAffected = jdbcTemplate.update(sql, customer.getName(), customer.getUpdatedDate(), customer.getMemberNumber());
            log.info("CUSTOMER UPDATE NAME RESULT = " + rowsAffected);
        }

        if (customer.getEmail() != null) {
            String sql = """
                    UPDATE t_customer
                    SET email = ?, updated_date = ?
                    WHERE member_number = ?
                    """;
            rowsAffected = jdbcTemplate.update(sql, customer.getEmail(), customer.getUpdatedDate(), customer.getMemberNumber());
            log.info("CUSTOMER UPDATE EMAIL RESULT = " + rowsAffected);
        }

        if (customer.getMobile() != null) {
            System.out.println(customer.getMobile());
            System.out.println(customer.getUpdatedDate());
            System.out.println(customer.getMemberNumber());
//            String sql = """
//                    UPDATE t_customer
//                    SET mobile_no = ?, updated_date = ?
//                    WHERE member_number = ?
//                    """;
            String sql = "UPDATE t_customer SET mobile_no = ? , updated_date = ?  WHERE member_number = ?";

            rowsAffected = jdbcTemplate.update(sql, customer.getMobile(), customer.getUpdatedDate(), customer.getMemberNumber());
            log.info("CUSTOMER UPDATE MOBILE RESULT = " + rowsAffected);
        }

        return rowsAffected > 0;
    }

    @Override
    public Boolean deleteCustomer(Customer customer) {
        final String sql = """
                DELETE FROM t_customer
                WHERE member_number = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql, customer.getMemberNumber());
        log.info("CUSTOMER DELETE RESULT = " + rowsAffected);
        return rowsAffected > 0;
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        final String sql = "SELECT COUNT(*) FROM t_customer WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
