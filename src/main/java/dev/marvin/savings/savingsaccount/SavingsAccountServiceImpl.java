package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.appuser.customer.Customer;
import dev.marvin.savings.appuser.customer.CustomerRepository;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.util.UniqueIDSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepository savingsAccountRepository;
    private final CustomerRepository customerRepository;
    private final UniqueIDSupplier<SavingsAccount> savingsAccountUniqueIDSupplier = new UniqueIDSupplier<>(SavingsAccount.class);

    @Override
    public SavingsAccount createAccount(NewSavingsAccountRequest accountRequest) {
        Customer customer = customerRepository.findByMemberNumber(accountRequest.memberNumber())
                .orElseThrow(() -> new ResourceNotFoundException("customer does not exist"));

        SavingsAccount savingsAccount =  SavingsAccount.builder()
                    .accountNumber(savingsAccountUniqueIDSupplier.get())
                    .accountName(accountRequest.accountName())
                    .savingsAccountType(SavingsAccountType.valueOf(accountRequest.accountType().toUpperCase()))
                    .balance(BigDecimal.ZERO)
                    .customer(customer)
                    .build();

        return savingsAccountRepository.save(savingsAccount);
    }

    @Override
    public List<SavingsAccount> getAllAccounts() {
        return savingsAccountRepository.findAll();
    }

    @Override
    public List<SavingsAccount> getAccountsByMemberNumber(String memberNumber) {
        return null;
    }

    @Override
    public List<SavingsAccount> getAccountsByAccountType(String accountType) {
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

}

