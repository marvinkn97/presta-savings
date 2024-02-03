package dev.marvin.savings.transaction.dto;

import lombok.Builder;

@Builder
public record TransactionResponse(
        String transactionCode,
        String transactionType,
        String paymentMethod,
        Double amount,
        Long createdDate,
        String accountNumber
) {
}
