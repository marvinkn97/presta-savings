package dev.marvin.savings.transaction.dao;

import dev.marvin.savings.transaction.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDaoImpl implements TransactionDao{

    private final JdbcTemplate jdbcTemplate;

    public TransactionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTransaction(Transaction transaction) {
    }
}
