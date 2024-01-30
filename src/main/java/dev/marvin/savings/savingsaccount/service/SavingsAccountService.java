package dev.marvin.savings.savingsaccount.service;

import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;

public interface SavingsAccountService {
    String createAccount(NewSavingsAccountRequest accountRequest);
}
