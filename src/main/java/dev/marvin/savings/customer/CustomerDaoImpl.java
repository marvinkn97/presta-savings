package dev.marvin.savings.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRowMapper customerRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String MEMBER_NUMBER_PARAM = "memberNumber";
    private static final String EMAIL_PARAM = "email";
    private static final String NAME_PARAM = "name";

    @Override
    public Boolean insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO t_customer (member_number)
                VALUES(:memberNumber)
                """;

        int rowsAffected = namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue(MEMBER_NUMBER_PARAM, customer.getMemberNumber(), Types.VARCHAR));
        log.info("CUSTOMER INSERT RESULT = " + rowsAffected);
        return rowsAffected > 0;
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                 ORDER BY created_date DESC
                  
                """;

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource(),
                customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomerByMemberNumber(String memberNumber) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE member_number = :memberNumber
                """;
        return namedParameterJdbcTemplate.query(sql,
                        new MapSqlParameterSource().addValue(MEMBER_NUMBER_PARAM, memberNumber),
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
                        new MapSqlParameterSource().addValue(EMAIL_PARAM, email)
                        , customerRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> getCustomerByName(String name) {
        String sql = """
                SELECT member_number, customer_name, email, password, mobile_no, government_id, created_date
                FROM t_customer
                WHERE customer_name = :name
                """;
        return namedParameterJdbcTemplate.query(sql,
                        new MapSqlParameterSource().addValue(NAME_PARAM, name),
                        customerRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Boolean updateCustomer(Customer update) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder sqlBuilder = new StringBuilder("UPDATE t_customer SET ");
//
//        if (update.getName() != null) {
//            sqlBuilder.append("customer_name = :name, ");
//            parameterSource.addValue("name", update.getName());
//        }

//        if (update.getEmail() != null) {
//            sqlBuilder.append("email = :email, ");
//            parameterSource.addValue(EMAIL_PARAM, update.getEmail());
//
//        }

        if (update.getMobile() != null) {
            sqlBuilder.append("mobile_no = :mobile, ");
            parameterSource.addValue("mobile", update.getMobile());
        }

        // Add updated_date and member_number to the SQL statement
        sqlBuilder.append("WHERE member_number = :memberNumber");
        parameterSource.addValue(MEMBER_NUMBER_PARAM, update.getMemberNumber());

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
                        .addValue(MEMBER_NUMBER_PARAM, customer.getMemberNumber()));
        log.info("CUSTOMER DELETE RESULT = " + rowsAffected);
        return rowsAffected > 0;
    }

    @Override
    public Boolean existsCustomerWithEmail(String email) {
        final String sql = "SELECT COUNT(*) FROM t_customer WHERE email = :email";
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource().addValue(EMAIL_PARAM, email), Integer.class);
        return count != null && count > 0;
    }
}
