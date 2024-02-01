package dev.marvin.savings.savingsaccount.service;

import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountUpdateRequest;

import java.util.List;

public interface SavingsAccountService {
    String createAccount(NewSavingsAccountRequest accountRequest);
    List<SavingsAccountResponse> getAllAccounts();
    List<SavingsAccountResponse> getAccountsByMemberNumber(String memberNumber);
    List<SavingsAccountResponse> getAccountsByAccountType(String accountType);
    SavingsAccountResponse getAccountByAccountNumber(String accountNumber);
    String updateAccount(String accountNumber, SavingsAccountUpdateRequest updateRequest);
//    void deleteAccount(SavingsAccount savingsAccount);


}
