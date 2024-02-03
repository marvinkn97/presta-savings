package dev.marvin.savings.transaction.service;

import dev.marvin.savings.transaction.dto.TransactionRequest;
import dev.marvin.savings.transaction.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    String makeTransaction(TransactionRequest transactionRequest);
    List<TransactionResponse> getAllTransactions();
    List<TransactionResponse> getAllTransactionsByAccountNumber(String accountNumber);
    TransactionResponse getTransactionByTransactionCode(String transactionCode);
}
