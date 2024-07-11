package dev.marvin.savings.util;

import dev.marvin.savings.dto.SavingsAccountResponse;
import dev.marvin.savings.entity.SavingsAccount;

public class Mapper {

    public static SavingsAccountResponse mapToSavingsAccountResponse(SavingsAccount savingsAccount) {
        return SavingsAccountResponse.builder()
                .accountNumber(savingsAccount.getAccountNumber())
                .accountName(savingsAccount.getAccountName())
                .accountType(savingsAccount.getSavingsAccountType().toString())
                .balance(savingsAccount.getBalance())
                .build();
    }
}
