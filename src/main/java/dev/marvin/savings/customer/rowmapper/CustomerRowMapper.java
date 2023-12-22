package dev.marvin.savings.customer.rowmapper;

import dev.marvin.savings.customer.entity.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
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
}
