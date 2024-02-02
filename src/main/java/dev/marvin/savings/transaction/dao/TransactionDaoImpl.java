package dev.marvin.savings.transaction.dao;

import dev.marvin.savings.transaction.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Transactional
@Slf4j
@Repository
public class TransactionDaoImpl implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void performTransaction(Transaction transaction) {
        Connection connection = null;
        final String insert = """
                INSERT INTO t_transaction (transaction_code, transaction_type, payment_method, amount, created_date, account_no)
                VALUES(?, ?, ?, ?, ?, ?)
                """;

        final String update = """
                UPDATE t_savings_account
                SET balance = ?
                WHERE account_number = ?
                """;

        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            connection.setAutoCommit(false);

            int rowsAffectedTx = jdbcTemplate.update(insert, transaction.getTransactionCode(), transaction.getTransactionType().name(), transaction.getPaymentMethod().name(), transaction.getAmount(), transaction.getCreatedDate(), transaction.getSavingsAccount().getAccountNumber());
            log.info("TRANSACTION INSERT RESULT = " + rowsAffectedTx);

            int rowsAffectedAcc = jdbcTemplate.update(update, transaction.getSavingsAccount().getBalance(), transaction.getSavingsAccount().getAccountNumber());
            log.info("SAVINGS ACCOUNT BALANCE UPDATE RESULT = " + rowsAffectedAcc);

        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                log.info(ex.getMessage());
            }
            log.info(e.getMessage());
        }
    }
}
