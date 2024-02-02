package dev.marvin.savings.savingsaccount.service;

import dev.marvin.savings.customer.dao.CustomerDao;
import dev.marvin.savings.customer.model.Customer;
import dev.marvin.savings.savingsaccount.dao.SavingsAccountDao;
import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountUpdateRequest;
import dev.marvin.savings.savingsaccount.model.SavingsAccount;
import dev.marvin.savings.savingsaccount.model.SavingsAccountType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountDao savingsAccountDao;
    private final CustomerDao customerDao;

    //TODO: refactor to functional style

    public SavingsAccountServiceImpl(SavingsAccountDao savingsAccountDao, CustomerDao customerDao) {
        this.savingsAccountDao = savingsAccountDao;
        this.customerDao = customerDao;
    }

    @Override
    public String createAccount(NewSavingsAccountRequest accountRequest) {
        Customer customer = customerDao.getCustomerByMemberNumber(accountRequest.memberNumber());

        if (customer != null) {
            SavingsAccount savingsAccount = SavingsAccount.builder()
                    .accountNumber(generateSavingsAccountNumber())
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

        return savingsAccounts.stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<SavingsAccountResponse> getAccountsByMemberNumber(String memberNumber) {
        List<SavingsAccount> savingsAccounts = savingsAccountDao.getAccountsByMemberNumber(memberNumber).stream()
                .peek(savingsAccount -> savingsAccount.setCustomer(customerDao.getCustomerByMemberNumber(savingsAccount.getCustomer().getMemberNumber())))
                .toList();
        return savingsAccounts.stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<SavingsAccountResponse> getAccountsByAccountType(String accountType) {
        List<SavingsAccount> savingsAccounts = savingsAccountDao.getAccountsByAccountType(accountType).stream()
                .peek(savingsAccount -> savingsAccount.setCustomer(customerDao.getCustomerByMemberNumber(savingsAccount.getCustomer().getMemberNumber())))
                .toList();
        return savingsAccounts.stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public SavingsAccountResponse getAccountByAccountNumber(String accountNumber) {
        SavingsAccount savingsAccount = savingsAccountDao.getAccountByAccountNumber(accountNumber);
        return mapEntityToDTO(savingsAccount);
    }

    @Override
    public String updateAccount(String accountNumber, SavingsAccountUpdateRequest updateRequest) {
        boolean changes = false;

        SavingsAccount savingsAccount = savingsAccountDao.getAccountByAccountNumber(accountNumber);

        SavingsAccount update;

        if (savingsAccount != null) {

            update = new SavingsAccount();
            update.setAccountNumber(savingsAccount.getAccountNumber());

            if (!updateRequest.accountName().isBlank() && !savingsAccount.getAccountName().equalsIgnoreCase(updateRequest.accountName())) {
               update.setAccountName(updateRequest.accountName());
                changes = true;
            }

            if (!changes) {
                return "No data changes found";
            }

            savingsAccountDao.updateAccount(update);
            return "Account updated successfully";
        } else {
            return "Account not found";
        }
    }

    @Override
    public String deleteAccount(String accountNumber) {
        SavingsAccount savingsAccount = savingsAccountDao.getAccountByAccountNumber(accountNumber);

        if (savingsAccount != null) {
            savingsAccountDao.deleteAccount(savingsAccount);
            return "Account [%s] deleted successfully".formatted(savingsAccount.getAccountNumber());
        } else {
            return "Account not found";
        }
    }

    private SavingsAccountResponse mapEntityToDTO(SavingsAccount savingsAccount) {
        return SavingsAccountResponse.builder()
                .accountNumber(savingsAccount.getAccountNumber())
                .accountName(savingsAccount.getAccountName())
                .accountType(savingsAccount.getSavingsAccountType().name())
                .createdDate(savingsAccount.getCreatedDate())
                .memberNumber(savingsAccount.getCustomer().getMemberNumber())
                .build();
    }

    private String generateSavingsAccountNumber() {
        String accountNumber = "ACC" + UUID.randomUUID().toString().substring(0, 6);
        return accountNumber.toUpperCase();
    }

}

