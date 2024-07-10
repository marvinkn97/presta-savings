package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.appuser.customer.Customer;
import dev.marvin.savings.dto.SavingsAccountRequest;
import dev.marvin.savings.dto.SavingsAccountResponse;
import dev.marvin.savings.entity.SavingsAccount;
import dev.marvin.savings.entity.SavingsAccountType;
import dev.marvin.savings.repository.SavingsAccountRepository;
import dev.marvin.savings.service.SavingsAccountService;
import dev.marvin.savings.util.SavingsAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
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
        }
        return response;
    }

    @Override
    public Map<String, Object> getAllAccounts() {
        var accounts = savingsAccountRepository.findAll();
        List<SavingsAccountResponse> response = new ArrayList<>();
        accounts.forEach(savingsAccount -> {
            var accountDTO = SavingsAccountMapper.mapToDTO(savingsAccount);
            response.add(accountDTO);
        });
        return null;
    }

    @Override
    public Map<String, Object> getAccountsByMemberNumber(String memberNumber) {
        var accounts = savingsAccountRepository.findByCustomerMemberNumber(memberNumber);

        List<SavingsAccountResponse> response = new ArrayList<>();

        accounts.forEach(savingsAccount -> {
            var accountDTO = SavingsAccountMapper.mapToDTO(savingsAccount);
            response.add(accountDTO);
        });

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

