package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.transaction.TransactionResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record SavingsAccountResponse(
        String accountNumber,
        String accountName,
        String accountType,
        BigDecimal balance,
        List<TransactionResponse> transactions
) {
}
