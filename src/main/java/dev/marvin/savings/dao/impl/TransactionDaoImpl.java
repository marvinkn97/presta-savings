package dev.marvin.savings.dao.impl;

import dev.marvin.savings.dao.TransactionDao;
import dev.marvin.savings.dao.rowmapper.TransactionRowMapper;
import dev.marvin.savings.model.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


@Slf4j
@Repository
public class TransactionDaoImpl implements TransactionDao {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper transactionRowMapper;

    public TransactionDaoImpl(JdbcTemplate jdbcTemplate, TransactionRowMapper transactionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionRowMapper = transactionRowMapper;
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

    @Override
    public List<Transaction> getAllTransactions() {
        final String sql = """
                SELECT transaction_code, transaction_type, payment_method, amount, created_date, account_no
                FROM t_transaction
                """;
        return jdbcTemplate.query(sql, transactionRowMapper);
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber) {
        final String sql = """
                SELECT transaction_code, transaction_type, payment_method, amount, created_date, account_no
                FROM t_transaction
                WHERE account_no = ?
                """;
        return jdbcTemplate.query(sql, transactionRowMapper, accountNumber);
    }

    @Override
    public Transaction getTransactionByTransactionCode(String transactionCode) {
        final String sql = """
                SELECT transaction_code, transaction_type, payment_method, amount, created_date, account_no
                FROM t_transaction
                WHERE transaction_code = ?
                """;
        return jdbcTemplate.queryForObject(sql, transactionRowMapper, transactionCode);
    }
}
