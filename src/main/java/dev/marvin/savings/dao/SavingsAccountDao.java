package dev.marvin.savings.dao;

import dev.marvin.savings.model.SavingsAccount;

import java.util.List;

public interface SavingsAccountDao {
    void insertAccount(SavingsAccount savingsAccount);
    List<SavingsAccount> getAllAccounts();
    List<SavingsAccount> getAccountsByMemberNumber(String memberNumber);
    List<SavingsAccount> getAccountsByAccountType(String accountType);

    //TODO: change return type to optional
    SavingsAccount getAccountByAccountNumber(String accountNumber);
    void updateAccount(SavingsAccount savingsAccount);
    void deleteAccount(SavingsAccount savingsAccount);


}
