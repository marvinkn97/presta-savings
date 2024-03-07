package dev.marvin.savings.service;

import dev.marvin.savings.dto.savingsaccount.NewSavingsAccountRequest;
import dev.marvin.savings.dto.savingsaccount.SavingsAccountResponse;
import dev.marvin.savings.dto.savingsaccount.SavingsAccountUpdateRequest;

import java.util.List;

public interface SavingsAccountService {
    String createAccount(NewSavingsAccountRequest accountRequest);
    List<SavingsAccountResponse> getAllAccounts();
    List<SavingsAccountResponse> getAccountsByMemberNumber(String memberNumber);
    List<SavingsAccountResponse> getAccountsByAccountType(String accountType);
    SavingsAccountResponse getAccountByAccountNumber(String accountNumber);
    String updateAccount(String accountNumber, SavingsAccountUpdateRequest updateRequest);
    String deleteAccount(String accountNumber);
    Double getAllCustomerAccountsTotalBalance(String memberNumber);
    Double getAllCustomersAccountTotalBalance();



}
