package dev.marvin.savings.savingsaccount.util;

import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;
import dev.marvin.savings.savingsaccount.model.SavingsAccount;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SavingsAccountUtil {

    public static String generateSavingsAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().substring(0, 6);
    }
    public static SavingsAccountResponse mapEntityToDTO(SavingsAccount savingsAccount){
        return SavingsAccountResponse.builder()
                .accountNumber(savingsAccount.getAccountNumber())
                .accountName(savingsAccount.getAccountName())
                .accountType(savingsAccount.getSavingsAccountType().name())
                .createdDate(savingsAccount.getCreatedDate())
                .memberNumber(savingsAccount.getCustomer().getMemberNumber())
                .build();
    }
}
