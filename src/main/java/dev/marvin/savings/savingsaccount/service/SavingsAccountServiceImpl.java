package dev.marvin.savings.savingsaccount.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.model.Customer;
import dev.marvin.savings.savingsaccount.dao.SavingsAccountDao;
import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;
import dev.marvin.savings.savingsaccount.model.SavingsAccount;
import dev.marvin.savings.savingsaccount.model.SavingsAccountType;
import dev.marvin.savings.savingsaccount.util.SavingsAccountUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountDao savingsAccountDao;
    private final CustomerDao customerDao;

    public SavingsAccountServiceImpl(SavingsAccountDao savingsAccountDao, CustomerDao customerDao) {
        this.savingsAccountDao = savingsAccountDao;
        this.customerDao = customerDao;
    }

    @Override
    public String createAccount(NewSavingsAccountRequest accountRequest) {
        Customer customer = customerDao.getCustomerByMemberNumber(accountRequest.memberNumber());

        if (customer != null) {
            SavingsAccount savingsAccount = SavingsAccount.builder()
                    .accountNumber(SavingsAccountUtil.generateSavingsAccountNumber().toUpperCase())
                    .accountName(accountRequest.accountName())
                    .savingsAccountType(SavingsAccountType.valueOf(accountRequest.accountType().toUpperCase()))
                    .balance(0.0)
                    .createdDate(System.currentTimeMillis())
                    .customer(customer)
                    .build();

            savingsAccountDao.insertAccount(savingsAccount);
            return "New Account Created successfully";
        } else {
            return "Error in creating account";
        }
    }

    @Override
    public List<SavingsAccountResponse> getAllAccounts() {
        List<SavingsAccount> savingsAccounts = savingsAccountDao.getAllAccounts().stream()
                .peek(savingsAccount -> savingsAccount.setCustomer(customerDao.getCustomerByMemberNumber(savingsAccount.getCustomer().getMemberNumber())))
                .toList();

        return savingsAccounts.stream().map(SavingsAccountUtil::mapEntityToDTO).collect(Collectors.toList());
    }
}

