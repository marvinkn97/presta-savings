package dev.marvin.savings.savingsaccount;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class SavingsAccountController {
    private final ISavingsAccountService ISavingsAccountService;

    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<SavingsAccount> createAccount(@RequestBody NewSavingsAccountRequest accountRequest) {
        SavingsAccount savingsAccount = ISavingsAccountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savingsAccount);
    }

    @GetMapping
    public List<SavingsAccount> getAllAccounts() {
        return ISavingsAccountService.getAllAccounts();
    }

    @GetMapping("/member/{memberNo}")
    public List<SavingsAccount> getAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber) {
        return ISavingsAccountService.getAccountsByMemberNumber(memberNumber);
    }

    @GetMapping("/type/{accountType}")
    public List<SavingsAccount> getAccountsByAccountType(@PathVariable("accountType") String accountType) {
        return ISavingsAccountService.getAccountsByAccountType(accountType);
    }

    @GetMapping("/{accountNo}")
    public SavingsAccount getAccountByAccountNumber(@PathVariable("accountNo") String accountNumber) {
        return ISavingsAccountService.getAccountByAccountNumber(accountNumber);
    }

    @DeleteMapping("/{accountNo}/delete")
    public String deleteAccount(@PathVariable("accountNo") String accountNumber) {
        ISavingsAccountService.deleteAccount(accountNumber);
        return null;
    }

    @GetMapping("/member/{memberNo}/balance")
    public Double getAllCustomerAccountsTotalBalance(@PathVariable("memberNo") String memberNumber) {
        return ISavingsAccountService.getAllCustomerAccountsTotalBalance(memberNumber);
    }

    @GetMapping("/all/total")
    public Double getAllCustomerAccountsTotalBalance() {
        return ISavingsAccountService.getAllCustomersAccountTotalBalance();
    }
}
