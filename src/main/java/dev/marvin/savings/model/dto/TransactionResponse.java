package dev.marvin.savings.model.dto;

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
