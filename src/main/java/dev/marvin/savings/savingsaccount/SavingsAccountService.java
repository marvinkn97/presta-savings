package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.appuser.customer.Customer;

import java.util.List;

public interface SavingsAccountService {
    void createAccount(SavingsAccountRequest accountRequest, Customer customer);
    List<SavingsAccountResponse> getAllAccounts();
    List<SavingsAccountResponse> getAccountsByMemberNumber(String memberNumber);
    void deleteAccount(String accountNumber);
    Double getAllCustomerAccountsTotalBalance(String memberNumber);
    Double getAllCustomersAccountTotalBalance();

}
