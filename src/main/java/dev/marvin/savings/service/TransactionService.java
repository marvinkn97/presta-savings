package dev.marvin.savings.service;

import dev.marvin.savings.dto.TransactionRequest;
import dev.marvin.savings.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction performTransaction(TransactionRequest transactionRequest);
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByAccountNumber(String accountNumber);
    Transaction getTransactionByTransactionCode(String transactionCode);
}
