package dev.marvin.savings.savingsaccount;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class SavingsAccountController {
    private final SavingsAccountService savingsAccountService;

    @PostMapping
    public ResponseEntity<SavingsAccount> createAccount(@RequestBody NewSavingsAccountRequest accountRequest) {
        SavingsAccount savingsAccount = savingsAccountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savingsAccount);
    }

    @GetMapping
    public List<SavingsAccount> getAllAccounts() {
        return savingsAccountService.getAllAccounts();
    }

    @GetMapping("/member/{memberNo}")
    public List<SavingsAccount> getAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber) {
        return savingsAccountService.getAccountsByMemberNumber(memberNumber);
    }

    @GetMapping("/type/{accountType}")
    public List<SavingsAccount> getAccountsByAccountType(@PathVariable("accountType") String accountType) {
        return savingsAccountService.getAccountsByAccountType(accountType);
    }

    @GetMapping("/{accountNo}")
    public SavingsAccount getAccountByAccountNumber(@PathVariable("accountNo") String accountNumber) {
        return savingsAccountService.getAccountByAccountNumber(accountNumber);
    }

    @DeleteMapping("/{accountNo}/delete")
    public String deleteAccount(@PathVariable("accountNo") String accountNumber) {
        savingsAccountService.deleteAccount(accountNumber);
        return null;
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