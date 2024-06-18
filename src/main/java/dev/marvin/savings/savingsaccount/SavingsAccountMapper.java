package dev.marvin.savings.savingsaccount;

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
