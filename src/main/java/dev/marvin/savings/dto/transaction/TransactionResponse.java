package dev.marvin.savings.dto.transaction;

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
