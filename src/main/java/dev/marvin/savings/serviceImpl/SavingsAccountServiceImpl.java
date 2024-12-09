package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.appuser.customer.Customer;
import dev.marvin.savings.dto.SavingsAccountRequest;
import dev.marvin.savings.dto.SavingsAccountResponse;
import dev.marvin.savings.entity.SavingsAccount;
import dev.marvin.savings.entity.SavingsAccountType;
import dev.marvin.savings.repository.SavingsAccountRepository;
import dev.marvin.savings.service.SavingsAccountService;
import dev.marvin.savings.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class SavingsAccountServiceImpl implements SavingsAccountService {

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Override
    public Map<String, Object> createAccount(SavingsAccountRequest accountRequest, Customer customer) {
        Map<String, Object> response = new HashMap<>();
        try {
            SavingsAccount savingsAccount = SavingsAccount.builder()
                    .accountNumber(UUID.randomUUID().toString())
                    .accountName(accountRequest.accountName().toUpperCase())
                    .savingsAccountType(SavingsAccountType.valueOf(accountRequest.accountType().toUpperCase()))
                    .balance(BigDecimal.ZERO)
                    .customer(customer)
                    .build();
            savingsAccountRepository.save(savingsAccount);

            response.put("status", "201");
            response.put("message", "Account Created Successfully");
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
        return response;
    }

    @Override
    public Map<String, Object> getAllAccounts() {
        log.info("Inside getAllAccounts of SavingsAccountServiceImpl");
        Map<String, Object> response = new HashMap<>();
        try {
            List<SavingsAccount> accounts = savingsAccountRepository.findAll();
            List<SavingsAccountResponse> accountsDTOs = accounts.stream()
                    .map(Mapper::mapToSavingsAccountResponse)
                    .toList();
            response.put("status", "200");
            response.put("payload", accountsDTOs);
        } catch (Exception e) {
            response.put("status", "500");
            response.put("msg", "Error Fetching Accounts");
        }
        return response;
    }

    @Override
    public Map<String, Object> getAccountsByMemberNumber(String memberNumber) {
        log.info("Inside getAllAccountsByMemberNumber of SavingsAccountServiceImpl");
        Map<String, Object> response = new HashMap<>();
        try {
            List<SavingsAccount> accounts = savingsAccountRepository.findByCustomerMemberNumber(memberNumber);
            List<SavingsAccountResponse> accountsDTOs = accounts.stream()
                    .map(Mapper::mapToSavingsAccountResponse)
                    .toList();
            response.put("status", "200");
            response.put("payload", accountsDTOs);
        } catch (Exception e) {
            response.put("status", "500");
            response.put("msg", "Error Fetching Accounts");
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteAccount(String accountNumber) {
        return null;
    }

    @Override
    public Map<String, Object> getAllCustomerAccountsTotalBalance(String memberNumber) {
        return null;
    }

    @Override
    public Map<String, Object> getAllCustomersAccountTotalBalance() {
        return null;
    }
}

