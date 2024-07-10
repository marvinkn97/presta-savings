package dev.marvin.savings.service;

import dev.marvin.savings.appuser.customer.Customer;
import dev.marvin.savings.dto.SavingsAccountRequest;

import java.util.Map;

public interface SavingsAccountService {
    Map<String, Object> createAccount(SavingsAccountRequest accountRequest, Customer customer);
    Map<String, Object> getAllAccounts();
    Map<String, Object> getAccountsByMemberNumber(String memberNumber);
    Map<String, Object> deleteAccount(String accountNumber);
    Map<String, Object> getAllCustomerAccountsTotalBalance(String memberNumber);
    Map<String, Object> getAllCustomersAccountTotalBalance();

}
