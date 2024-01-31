package dev.marvin.savings.savingsaccount.service;

import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;

import java.util.List;

public interface SavingsAccountService {
    String createAccount(NewSavingsAccountRequest accountRequest);
    List<SavingsAccountResponse> getAllAccounts();
    List<SavingsAccountResponse> getAccountsByMemberNumber(String memberNumber);
    List<SavingsAccountResponse> getAccountsByAccountType(String accountType);

//    void updateAccount(SavingsAccount savingsAccount);
//    void deleteAccount(SavingsAccount savingsAccount);


}
