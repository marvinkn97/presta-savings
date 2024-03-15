package dev.marvin.savings.dao;

import dev.marvin.savings.model.Transaction;

import java.util.List;

public interface TransactionDao {
    void performTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByAccountNumber(String accountNumber);
    Transaction getTransactionByTransactionCode(String transactionCode);
}
