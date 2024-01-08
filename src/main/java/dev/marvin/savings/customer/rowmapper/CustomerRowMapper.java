package dev.marvin.savings.customer.rowmapper;

import dev.marvin.savings.customer.entity.Customer;
import dev.marvin.savings.customer.entity.Deleted;
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
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .mobile(rs.getInt("mobile"))
                .governmentId(rs.getInt("government_id"))
                .createdDate(rs.getLong("created_date"))
                .isDeleted(Deleted.valueOf(rs.getString("is_deleted")))
                .build();
    }
}
