package dev.marvin.savings.service;

import dev.marvin.savings.dto.SavingsAccountRequest;
import dev.marvin.savings.entity.UserEntity;

import java.util.Map;

public interface SavingsAccountService {
    void createAccount(SavingsAccountRequest accountRequest, UserEntity userEntity);
    Map<String, Object> getAllAccounts();
    Map<String, Object> getAccountsByMemberNumber(String memberNumber);
    Map<String, Object> deleteAccount(String accountNumber);
    Map<String, Object> getAllCustomerAccountsTotalBalance(String memberNumber);
    Map<String, Object> getAllCustomersAccountTotalBalance();

}
