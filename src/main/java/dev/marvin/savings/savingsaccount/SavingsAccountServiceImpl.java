package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.appuser.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepository savingsAccountRepository;

    @Override
    public void createAccount(SavingsAccountRequest accountRequest, Customer customer) {
        SavingsAccount savingsAccount = SavingsAccount.builder()
                .accountNumber(generateAccountNumber())
                .accountName(accountRequest.accountName().toUpperCase())
                .savingsAccountType(SavingsAccountType.valueOf(accountRequest.accountType().toUpperCase()))
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();

        savingsAccountRepository.save(savingsAccount);
    }

    @Override
    public List<SavingsAccountResponse> getAllAccounts() {
        var accounts = savingsAccountRepository.findAll();
        List<SavingsAccountResponse> response = new ArrayList<>();
        accounts.forEach(savingsAccount -> {
            var accountDTO = SavingsAccountMapper.mapToDTO(savingsAccount);
            response.add(accountDTO);
        });
        return response;
    }

    @Override
    public List<SavingsAccount> getAccountsByMemberNumber(String memberNumber) {
        return null;
    }

    @Override
    public SavingsAccount getAccountByAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public void deleteAccount(String accountNumber) {
    }

    @Override
    public Double getAllCustomerAccountsTotalBalance(String memberNumber) {
        return null;
    }

    @Override
    public Double getAllCustomersAccountTotalBalance() {
        return null;
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }
}

