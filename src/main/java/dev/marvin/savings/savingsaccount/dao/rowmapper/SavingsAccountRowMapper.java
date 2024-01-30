package dev.marvin.savings.savingsaccount.dao.rowmapper;

import dev.marvin.savings.customer.model.Customer;
import dev.marvin.savings.savingsaccount.model.SavingsAccount;
import dev.marvin.savings.savingsaccount.model.SavingsAccountType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SavingsAccountRowMapper implements RowMapper<SavingsAccount> {

    @Override
    public SavingsAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = Customer.builder().memberNumber(rs.getString("member_no")).build();

        return SavingsAccount.builder()
                .accountNumber(rs.getString("account_number"))
                .accountName(rs.getString("account_name"))
                .savingsAccountType(SavingsAccountType.valueOf(rs.getString("account_type")))
                .balance(rs.getDouble("balance"))
                .createdDate(rs.getLong("created_date"))
                .customer(customer)
                .build();
    }
}
