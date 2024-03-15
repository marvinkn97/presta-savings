package dev.marvin.savings.model.dto;

import jakarta.validation.constraints.NotBlank;

public record TransactionRequest(
        @NotBlank(message = "Account Number is required")
        String accountNumber,
        String transactionType,
        String paymentMethod,

//        @Min(value = 0, message = "Amount should be greater than zero")
//        @Positive(message = "Amount should be greater than zero")
        Double amount) {
}
