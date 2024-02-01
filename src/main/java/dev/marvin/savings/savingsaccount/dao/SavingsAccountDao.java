package dev.marvin.savings.savingsaccount.dao;

import dev.marvin.savings.savingsaccount.model.SavingsAccount;

import java.util.List;

public interface SavingsAccountDao {
    void insertAccount(SavingsAccount savingsAccount);
    List<SavingsAccount> getAllAccounts();
    List<SavingsAccount> getAccountsByMemberNumber(String memberNumber);
    List<SavingsAccount> getAccountsByAccountType(String accountType);
    SavingsAccount getAccountByAccountNumber(String accountNumber);
    void updateAccount(SavingsAccount savingsAccount);
    void deleteAccount(SavingsAccount savingsAccount);


}
