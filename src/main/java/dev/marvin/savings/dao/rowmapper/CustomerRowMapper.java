package dev.marvin.savings.dao.rowmapper;

import dev.marvin.savings.model.customer.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setMemberNumber(rs.getString("member_number"));
        customer.setMemberNumber(rs.getString("customer_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        customer.setMobile(rs.getString("mobile_no"));
        customer.setGovernmentId(rs.getInt("government_id"));
        customer.setCreatedDate(rs.getLong("created_date"));

        return customer;
    }
}
