package dev.marvin.savings.dao.impl;

import dev.marvin.savings.dao.CustomerDao;
import dev.marvin.savings.dao.rowmapper.CustomerRowMapper;
import dev.marvin.savings.model.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "jdbc")
@Slf4j
@Primary
@RequiredArgsConstructor
public class CustomerDaoJdbcImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO t_customer (member_number, customer_name, email, password, created_date)
                VALUES(:memberNumber, :name, :email, :password, :createdDate)
                """;

        int rowsAffected = namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("memberNumber", customer.getMemberNumber())
                        .addValue("name", customer.getName())
                        .addValue("email", customer.getEmail())
                        .addValue("password", customer.getPassword())
                        .addValue("createdDate", customer.getCreatedDate())
        );
        log.info("CUSTOMER INSERT RESULT = " + rowsAffected);
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                """;
        return namedParameterJdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomerByMemberNumber(String memberNumber) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE member_number = :memberNumber
                """;
        return namedParameterJdbcTemplate.query(sql,
                        new MapSqlParameterSource().addValue("memberNumber", memberNumber),
                        customerRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE email = :email
                """;

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource().addValue("email", email)
                , customerRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Boolean updateCustomer(Customer update) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder sqlBuilder = new StringBuilder("UPDATE t_customer SET ");

        if (update.getName() != null) {
            sqlBuilder.append("customer_name = :name, ");
            parameterSource.addValue("name", update.getName());
        }

        if (update.getEmail() != null) {
            sqlBuilder.append("email = :email, ");
            parameterSource.addValue("email", update.getEmail());

        }

        if (update.getMobile() != null) {
            sqlBuilder.append("mobile_no = :mobile, ");
            parameterSource.addValue("mobile", update.getMobile());
        }

        // Add updated_date and member_number to the SQL statement
        sqlBuilder.append("updated_date = :updatedDate WHERE member_number = :memberNumber");
        parameterSource.addValue("updatedDate", update.getUpdatedDate());
        parameterSource.addValue("memberNumber", update.getMemberNumber());

        // Execute the SQL update statement
       int rowsAffected = namedParameterJdbcTemplate.update(sqlBuilder.toString(), parameterSource);

        // Log the result of the update operation
        log.info("CUSTOMER UPDATE RESULT = " + rowsAffected);

        // Return true if rows were affected, false otherwise
        return rowsAffected > 0;
    }

    @Override
    public Boolean deleteCustomer(Customer customer) {
        final String sql = """
                DELETE FROM t_customer
                WHERE member_number = :memberNumber
                """;
        int rowsAffected = namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("memberNumber", customer.getMemberNumber()));
        log.info("CUSTOMER DELETE RESULT = " + rowsAffected);
        return rowsAffected > 0;
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        final String sql = "SELECT COUNT(*) FROM t_customer WHERE email = :email";
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource().addValue("email", email), Integer.class);
        return count != null && count > 0;
    }
}
