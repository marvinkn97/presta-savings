package dev.marvin.savings.transaction.service;

import dev.marvin.savings.transaction.dto.TransactionRequest;

public interface TransactionService {
    String makeTransaction(TransactionRequest transactionRequest);
}
