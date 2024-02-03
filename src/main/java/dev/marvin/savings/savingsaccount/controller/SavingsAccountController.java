package dev.marvin.savings.savingsaccount.controller;

import dev.marvin.savings.savingsaccount.dto.NewSavingsAccountRequest;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountResponse;
import dev.marvin.savings.savingsaccount.dto.SavingsAccountUpdateRequest;
import dev.marvin.savings.savingsaccount.service.SavingsAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    public SavingsAccountController(SavingsAccountService savingsAccountService) {
        this.savingsAccountService = savingsAccountService;
    }

    @PostMapping(value = "/create")
    public String createAccount(@RequestBody NewSavingsAccountRequest accountRequest) {
        return savingsAccountService.createAccount(accountRequest);
    }

    @GetMapping("/all")
    public List<SavingsAccountResponse> getAllAccounts() {
        return savingsAccountService.getAllAccounts();
    }

    @GetMapping("/member/{memberNo}")
    public List<SavingsAccountResponse> getAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber) {
        return savingsAccountService.getAccountsByMemberNumber(memberNumber);
    }

    @GetMapping("/type/{accountType}")
    public List<SavingsAccountResponse> getAccountsByAccountType(@PathVariable("accountType") String accountType) {
        return savingsAccountService.getAccountsByAccountType(accountType);
    }

    @GetMapping("/{accountNo}")
    public SavingsAccountResponse getAccountByAccountNumber(@PathVariable("accountNo") String accountNumber) {
        return savingsAccountService.getAccountByAccountNumber(accountNumber);
    }

    @PutMapping("/update/{accountNo}")
    public String updateAccount(@PathVariable("accountNo") String accountNumber, @RequestBody SavingsAccountUpdateRequest updateRequest) {
        return savingsAccountService.updateAccount(accountNumber, updateRequest);
    }

    @DeleteMapping("/delete/{accountNo}")
    public String deleteAccount(@PathVariable("accountNo") String accountNumber) {
        return savingsAccountService.deleteAccount(accountNumber);
    }

    @GetMapping("/member/{memberNo}/balance")
    public Double getAllCustomerAccountsTotalBalance(@PathVariable("memberNo") String memberNumber) {
        return savingsAccountService.getAllCustomerAccountsTotalBalance(memberNumber);
    }

    @GetMapping("/all/total")
    public Double getAllCustomerAccountsTotalBalance() {
        return savingsAccountService.getAllCustomersAccountTotalBalance();
    }
}
