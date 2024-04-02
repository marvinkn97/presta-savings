package dev.marvin.savings.dao.rowmapper;

import dev.marvin.savings.savingsaccount.SavingsAccount;
import dev.marvin.savings.model.enums.PaymentMethod;
import dev.marvin.savings.model.Transaction;
import dev.marvin.savings.model.enums.TransactionType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountNumber(rs.getString("account_no"));

        return Transaction.builder()
                .transactionCode(rs.getString("transaction_code"))
                .transactionType(TransactionType.valueOf(rs.getString("transaction_type")))
                .paymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")))
                .amount(rs.getDouble("amount"))
                .createdDate(rs.getLong("created_date"))
                .savingsAccount(savingsAccount)
                .build();
    }
}
