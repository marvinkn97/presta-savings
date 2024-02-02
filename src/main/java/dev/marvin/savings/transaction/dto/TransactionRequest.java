package dev.marvin.savings.transaction.dto;

public record TransactionRequest(
        String accountNumber,
        String transactionType,
        String paymentMethod,
        Double amount) {
}
