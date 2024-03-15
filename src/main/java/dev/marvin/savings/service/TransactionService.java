package dev.marvin.savings.service;

import dev.marvin.savings.model.dto.TransactionRequest;
import dev.marvin.savings.model.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    String makeTransaction(TransactionRequest transactionRequest);
    List<TransactionResponse> getAllTransactions();
    List<TransactionResponse> getAllTransactionsByAccountNumber(String accountNumber);
    TransactionResponse getTransactionByTransactionCode(String transactionCode);
}
