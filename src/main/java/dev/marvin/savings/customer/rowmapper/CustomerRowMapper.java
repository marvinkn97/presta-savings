package dev.marvin.savings.customer.rowmapper;

import dev.marvin.savings.customer.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Customer.builder()
                .memberNumber(rs.getString("member_number"))
                .name(rs.getString("customer_name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .mobile(rs.getString("mobile_no"))
                .governmentId(rs.getInt("government_id"))
                .createdDate(rs.getLong("created_date"))
                .build();
    }
}
