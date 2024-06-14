package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.appuser.customer.Customer;

import java.util.List;

public interface SavingsAccountService {
    SavingsAccount createAccount(NewSavingsAccountRequest accountRequest, Customer customer);
    List<SavingsAccount> getAllAccounts();
    List<SavingsAccount> getAccountsByMemberNumber(String memberNumber);
    List<SavingsAccount> getAccountsByAccountType(String accountType);
    SavingsAccount getAccountByAccountNumber(String accountNumber);
    void deleteAccount(String accountNumber);
    Double getAllCustomerAccountsTotalBalance(String memberNumber);
    Double getAllCustomersAccountTotalBalance();

}
