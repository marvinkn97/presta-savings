package dev.marvin.savings.savingsaccount.service;

import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;

import java.util.List;

public interface SavingsAccountService {
    String createAccount(NewSavingsAccountRequest accountRequest);
    List<SavingsAccountResponse> getAllAccounts();
}
