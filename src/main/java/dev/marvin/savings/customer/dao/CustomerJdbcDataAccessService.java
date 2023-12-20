package dev.marvin.savings.customer.dao;

import dev.marvin.savings.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                SELECT name, email, mobile, government_id, member_number
                FROM tlb_customers
                """;
        return jdbcTemplate.query(sql, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setMobile(rs.getInt("mobile"));
                customer.setGovernmentId(rs.getInt("government_id"));
                customer.setMemberNumber(rs.getString("member_number"));
                return customer;
            }
        });
    }

    @Override
    public Optional<Customer> getCustomerById(Integer customerId) {
        String sql = """
                SELECT id, name, email, mobile, government_id, member_number
                FROM tbl_customers
                WHERE id = ?
                """;
        return null;
    }

    @Override
    public Integer insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO tbl_customers (name, email, mobile, government_id, member_number)
                VALUES(?,?,?,?,?)
                """;
        return jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getMobile(), customer.getGovernmentId(), customer.getMemberNumber());
    }

    @Override
    public Boolean updateCustomer(Integer customerId) {
        String sql = """
                UPDATE tbl_customers 
                SET name = ?
                WHERE id = ?
                """;

        return false;
    }

    @Override
    public Boolean deleteCustomer(Integer customerId) {
        String sql = """
                DELETE FROM tbl_customers
                WHERE id = ?
                """;

        return false;
    }
}
