package dev.marvin.savings.savingsaccount;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.config.AppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class SavingsAccountController {
    private final SavingsAccountService savingsAccountService;

    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<AppResponse> createAccount(@RequestBody NewSavingsAccountRequest accountRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var appUser = (AppUser) authentication.getPrincipal();

        AppResponse appResponse = AppResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.value())
                .reason(HttpStatus.CREATED.getReasonPhrase())
                .data("Savings Account Created Successfully")
                .build();

         savingsAccountService.createAccount(accountRequest, appUser.getCustomer());

        return ResponseEntity.status(HttpStatus.CREATED).body(appResponse);
    }

    @GetMapping
    public List<SavingsAccount> getAllAccounts() {
        return savingsAccountService.getAllAccounts();
    }

    @GetMapping("/member/{memberNo}")
    public List<SavingsAccount> getAccountsByMemberNumber(@PathVariable("memberNo") String memberNumber) {
        return savingsAccountService.getAccountsByMemberNumber(memberNumber);
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
