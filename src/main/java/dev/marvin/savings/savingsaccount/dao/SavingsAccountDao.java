package dev.marvin.savings.savingsaccount.dao;

import dev.marvin.savings.savingsaccount.model.SavingsAccount;

import java.util.List;

public interface SavingsAccountDao {
    void createAccount(SavingsAccount savingsAccount);
    List<SavingsAccount> getAllAccounts();
    List<SavingsAccount> getAllAccountsByMemberNumber(String memberNumber);
    SavingsAccount getAccountByAccountNumber(String accountNumber);
    void updateAccount(SavingsAccount savingsAccount);
    void deleteAccount(SavingsAccount savingsAccount);
}
