package dev.marvin.savings.transaction.dao;

import dev.marvin.savings.transaction.model.Transaction;

public interface TransactionDao {
    void performTransaction(Transaction transaction);
}
