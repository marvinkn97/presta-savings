package dev.marvin.savings.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(name = "Transaction Request")
public record TransactionRequest(
        @Schema(name = "Account Number", defaultValue = "ACC12345678")
        @NotBlank(message = "Account Number is required")
        String accountNumber,

        @Schema(name = "Transaction Type", defaultValue = "DEPOSIT")
        @NotBlank(message = "Transaction Type is required")
        String transactionType,

        @Schema(name = "Payment Method", defaultValue = "BANK")
        @NotBlank(message = "Payment Method is required")
        String paymentMethod,

        @Schema(name = "Amount", defaultValue = "100.00")
        @Positive(message = "Amount should be greater than zero")
        BigDecimal amount) {
}
