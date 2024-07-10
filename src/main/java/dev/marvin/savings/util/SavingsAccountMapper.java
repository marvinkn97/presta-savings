package dev.marvin.savings.util;

import dev.marvin.savings.dto.SavingsAccountResponse;
import dev.marvin.savings.entity.SavingsAccount;

public class SavingsAccountMapper {

    public static SavingsAccountResponse mapToDTO(SavingsAccount savingsAccount) {
        return SavingsAccountResponse.builder()
                .accountNumber(savingsAccount.getAccountNumber())
                .accountName(savingsAccount.getAccountName())
                .accountType(savingsAccount.getSavingsAccountType().toString())
                .balance(savingsAccount.getBalance())
                .build();
    }
}
