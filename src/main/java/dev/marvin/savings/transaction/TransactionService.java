package dev.marvin.savings.transaction;

import java.util.List;

public interface TransactionService {
    Transaction performTransaction(TransactionRequest transactionRequest);
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByAccountNumber(String accountNumber);
    Transaction getTransactionByTransactionCode(String transactionCode);
}
