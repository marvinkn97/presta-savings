package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.dto.SavingsAccountRequest;
import dev.marvin.savings.dto.SavingsAccountResponse;
import dev.marvin.savings.entity.SavingsAccount;
import dev.marvin.savings.entity.SavingsAccountType;
import dev.marvin.savings.entity.UserEntity;
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
    public void createAccount(SavingsAccountRequest accountRequest, UserEntity userEntity) {
            SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setAccountNumber(UUID.randomUUID().toString());
            savingsAccount.setAccountName(accountRequest.accountName().toUpperCase());
            savingsAccount.setSavingsAccountType(SavingsAccountType.valueOf(accountRequest.accountType().toUpperCase()));
            savingsAccount.setBalance(BigDecimal.ZERO);
            savingsAccount.setUserEntity(userEntity);
            savingsAccountRepository.save(savingsAccount);
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
       return null;
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

